package models.excel;

import models.Resource;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 9:32 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "worksheet")
public class Worksheet extends Resource {
}
