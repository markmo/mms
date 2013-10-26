package models.domain.excel;

import javax.persistence.*;

import models.domain.file.FileResource;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 9:29 PM
 */
@Entity
@DiscriminatorValue("XLS")
public class ExcelResource extends FileResource {}
