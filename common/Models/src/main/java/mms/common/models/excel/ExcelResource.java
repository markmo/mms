package mms.common.models.excel;

import javax.persistence.*;

import mms.common.models.file.FileResource;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 9:29 PM
 */
@Entity
@DiscriminatorValue("XLS")
public class ExcelResource extends FileResource {}
