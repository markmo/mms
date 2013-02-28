package nz.co.datascience.mms.extractors;

import static org.apache.poi.ss.usermodel.Cell.*;
import static nz.co.datascience.mms.model.DataTypeFactory.*;

import nz.co.datascience.mms.analyzers.ColumnAnalyzer;
import nz.co.datascience.mms.analyzers.ConnectedComponentAnalyzer;
import nz.co.datascience.mms.analyzers.PropertyBag;
import nz.co.datascience.mms.util.Counter;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.AreaPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRow;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 11:56 AM
 */
public class ExcelExtractor {

    private static Map<String, Region> regions = new HashMap<String, Region>();

    public static void main(String[] args) throws Exception {
        InputStream inp = new FileInputStream("/Users/markmo/Downloads/Electricity.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(inp);
        wb.setMissingCellPolicy(org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL); // doesn't seem to make a difference
        Sheet sheet = wb.getSheet("Annual GWh");
//        for (Sheet sheet : wb) {
            System.out.println(sheet.getSheetName());
            System.out.println("======================================================");
            Region currentRegion = null;

        // Construct matrix
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            int height = lastRowNum - firstRowNum + 1;
            int firstColumnIndex = 2^14; // max columns in Excel 2010
            int lastColumnIndex = 0;
            for (org.apache.poi.ss.usermodel.Row row : sheet) {
                int firstCellNum = row.getFirstCellNum();

                // TODO. Does the Sheet iterator include a row with no cell values?
                if (firstCellNum != -1) { // the row has at least one cell
                    firstColumnIndex = Math.min(firstColumnIndex, row.getFirstCellNum());
                    lastColumnIndex = Math.max(lastColumnIndex, row.getLastCellNum() - 1);
                }
            }
            int width = lastColumnIndex - firstColumnIndex + 1;
            int[][] matrix = new int[width][height];

            // zero initialize the matrix
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    matrix[i][j] = 0;
                }
            }

            List<Cell> cells = new ArrayList<Cell>();

            XSSFSheet xsheet = (XSSFSheet)sheet;

            for (org.apache.poi.ss.usermodel.Row row : sheet) {
                CTRow ctRow = ((XSSFRow)row).getCTRow();
                int ol = ctRow.getOutlineLevel();
                int rowNum = row.getRowNum();
                for (org.apache.poi.ss.usermodel.Cell cell : row) {
                    int columnIndex = cell.getColumnIndex();
                    CellReference cellRef = new CellReference(rowNum, columnIndex);
                    Cell mycell = new Cell();
                    mycell.setCellRef(cellRef.formatAsString());
                    mycell.setColumnIndex(columnIndex);
                    mycell.setRowNum(rowNum);

                    CellStyle style = cell.getCellStyle();
                    mycell.setIndentLevel(style.getIndention());

                    int cellType = cell.getCellType();
                    int cellContent = 0;

                    switch (cellType) {

                        case CELL_TYPE_STRING:
                            mycell.setDataType(getDataType(CELL_TYPE_STRING));
                            mycell.setStringValue(cell.getRichStringCellValue().getString());
                            cellContent = 1;
                            break;

                        case CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                mycell.setDataType(getDataType("Date"));
                                mycell.setDateValue(cell.getDateCellValue());
                            } else {
                                mycell.setDataType(getDataType(CELL_TYPE_NUMERIC));
                                mycell.setNumericValue(cell.getNumericCellValue());
                            }
                            cellContent = 1;
                            break;

                        case CELL_TYPE_BOOLEAN:
                            mycell.setDataType(getDataType(CELL_TYPE_BOOLEAN));
                            mycell.setBooleanValue(cell.getBooleanCellValue());
                            cellContent = 1;
                            break;

                        case CELL_TYPE_FORMULA:
                            switch (cell.getCachedFormulaResultType()) {

                                case CELL_TYPE_STRING:
                                    mycell.setDataType(getDataType(CELL_TYPE_STRING));
                                    break;

                                case CELL_TYPE_NUMERIC:
                                    mycell.setDataType(getDataType(CELL_TYPE_NUMERIC));
                                    break;

                                case CELL_TYPE_BOOLEAN:
                                    mycell.setDataType(getDataType(CELL_TYPE_BOOLEAN));
                                    break;

                                case CELL_TYPE_ERROR:
                                    // TODO
                                    break;
                            }
                            mycell.setDataType(getDataType(cell.getCachedFormulaResultType()));
                            String formula = cell.getCellFormula();
                            XSSFEvaluationWorkbook ewb = XSSFEvaluationWorkbook.create(wb);
                            Ptg[] tokens = FormulaParser.parse(formula, ewb, FormulaType.CELL, 0);
                            for (int i = 0; i < tokens.length; i++) {
                                Ptg token = tokens[i];
                                System.out.println(token.toFormulaString());
                                if (token instanceof AreaPtg) {
                                    AreaReference areaRef = new AreaReference(token.toFormulaString());
                                    CellReference[] cellRefs = areaRef.getAllReferencedCells();
                                    System.out.println(cellRefs.length);
                                }
                            }
                            mycell.setStringValue(formula);
                            cellContent = 1;
                            break;

                        case CELL_TYPE_ERROR:
                            // TODO
                            break;

                        /**
                         * If you put some data into a cell, then delete it again, Excel will
                         * normally still store the cell in the file, but with type blank. If you
                         * have never put anything in the cell, then it won't appear in the file.
                         */
                        case CELL_TYPE_BLANK:
                            // TODO
                            mycell.setDataType(getDataType(CELL_TYPE_BLANK));
                            break;
                    }
                    matrix[columnIndex - firstColumnIndex][rowNum - firstRowNum] = cellContent;
                    cells.add(mycell);
                }
            }

            // TODO. Possible issue - blank cells are being treated as connected cells.
            ConnectedComponentAnalyzer componentAnalyzer = new ConnectedComponentAnalyzer();

            // flatten matrix
            int[] image = new int[width * height];
            System.out.println("Image length: " + image.length);
            int k = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    image[k++] = matrix[j][i];
                }
            }

            int[] labels = componentAnalyzer.compactLabeling(image, new Dimension(width, height), true);

            // convert labels back to matrix
            int[][] labeledMatrix = new int[width][height];
            for (int i = 0; i < labels.length; i++) {
                int w = i % width;
                int h = i / width;
                labeledMatrix[i % width][i / width] = labels[i];
            }

            System.out.println("\nLabeled Matrix:\n");
            for (int c = 0; c < labeledMatrix.length; c++) {
                String alphaCol = CellReference.convertNumToColString(c);
                if (c == 0) {
                    System.out.print(String.format("%1$6s", alphaCol));
                } else {
                    System.out.print(String.format("%1$3s", alphaCol));
                }
            }
            System.out.println();
            for (int r = 0; r < labeledMatrix[0].length; r++) {
                System.out.print(String.format("%1$3s", r + 1));
                for (int c = 0; c < labeledMatrix.length; c++) {
                    int label = labeledMatrix[c][r];
                    System.out.print(String.format("%1$3s", (label == 0) ? "" : label));
                }
                System.out.println();
            }
            System.out.println();

            for (int i = 0; i < componentAnalyzer.getMaxLabel(); i++) {
                Region region = new Region();
                region.setId(i + 1);
                regions.put(sheet.getSheetName() + (i + 1), region);
            }

            for (Cell mycell : cells) {
                Region region = regions.get(sheet.getSheetName() + (labeledMatrix[mycell.getColumnIndex() - firstColumnIndex][mycell.getRowNum() - firstRowNum]));
                if (region != null) {
                    region.addCell(mycell);
                }
            }


            // Merge related regions
            List<Region> sortedRegions = new ArrayList<Region>(regions.values());
            Collections.sort(sortedRegions, new RegionComparator());
            Map<String, Region> newRegions = new HashMap<String, Region>();
            Region lastRegion = null;
            for (Region region : sortedRegions) {
                if (lastRegion != null) {
                    if (region.isRegionUnderAndMatchesWidth(lastRegion))
                    {
                        lastRegion.mergeRegion(region);
                    } else if (region.getWidth() < 3 &&
                            region.getFirstCell().getStringValue().toLowerCase().contains("note"))
                    {
                        // possibly an annotation
                        lastRegion.mergeRegionAsAnnotations(region);
                    } else if (region.isRegionAbove(lastRegion) &&
                            lastRegion.getWidth() < 3 &&
                            lastRegion.getHeight() < 3 &&
                            lastRegion.distanceFrom(region) < 3)
                    {
                        // possible heading
                        region.mergeRegionAsHeadings(lastRegion);
                        lastRegion = region;
                    } else {
                        newRegions.put(sheet.getSheetName() + lastRegion.getId(), lastRegion);
                        lastRegion = region;
                    }
                } else {
                    lastRegion = region;
                }
            }
            newRegions.put(sheet.getSheetName() + lastRegion.getId(), lastRegion);
            regions = newRegions;
//        }

        ColumnAnalyzer columnAnalyzer = new ColumnAnalyzer();
        for (Region region : regions.values()) {
            Set<Integer> headerIndexGuesses = new HashSet<Integer>();

            // process columns
            for (Column column : region) {
                column.setName(column.getCells().get(0).getStringValue());
                PropertyBag columnStats = (PropertyBag)columnAnalyzer.analyze(column.getValues());
                Counter<String> valueCounter = (Counter)columnStats.get("valueCounter");
                Counter<String> typeCounter = (Counter)columnStats.get("typeCounter");

                for (int i = 0; i < 1; i++) {
                    Cell cell = column.getCells().get(i);
                    if (valueCounter.getCount(cell.getStringValue()) == 1 && typeCounter.getCount(cell.getDataType().getName()) == 1) {
                        headerIndexGuesses.add(i);
                    }
                }
            }
            boolean hasHeaderRow = headerIndexGuesses.contains(0);
            for (Column column : region) {
                column.setHasHeaderRow(hasHeaderRow);
                column.setHidden(sheet.isColumnHidden(column.getColumnIndex()));
                PropertyBag columnStats = (PropertyBag)columnAnalyzer.analyze(column.getValues());
                boolean isUniform = (Boolean)columnStats.get("isUniform");
                String dataType = (String)columnStats.get("type");
                boolean isStringType = dataType.equals("String");
                boolean isDateType = dataType.equals("Date");
                boolean isIntegerType = ((List)columnStats.get("integerIndexes")).size() > 0;
                Set t = ((Counter)columnStats.get("valueCounter")).keySet();
                int s = column.getValues().size();
                double variability = (double)((Counter)columnStats.get("valueCounter")).keySet().size() / column.getValues().size();
                boolean isPossibleDimension = (isUniform && (isStringType || isIntegerType || isDateType) && (variability < 0.5));
                column.setDimension(isPossibleDimension);
//                System.out.println("\nColumn: " + column.getName());
//                System.out.println("isDimension: " + isPossibleDimension);
//                for (Object key : columnStats.keySet()) {
//                    System.out.println(key + ": " + columnStats.get(key));
//                }
            }

            // process rows

            for (Row row : region.getRows()) {
                int maxIndentLevel = 0;
                for (Cell cell : row) {
                    maxIndentLevel = Math.max(maxIndentLevel, cell.getIndentLevel());
                }
                row.setMaxIndentLevel(maxIndentLevel);
            }

            boolean rowSumsBelow = xsheet.getRowSumsBelow();
            boolean rowSumsRight = xsheet.getRowSumsRight();
            for (Row row : region.getRows()) {
                row.setOutlineLevel(1);
            }
            region.print();
        }
    }
}
