package models.excel;

import models.file.FileResource;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 9:29 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "excel_resource")
public class ExcelResource extends FileResource {
}
