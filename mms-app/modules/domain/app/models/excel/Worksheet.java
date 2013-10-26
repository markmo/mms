package models.domain.excel;

import javax.persistence.*;

import models.domain.Resource;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 9:32 PM
 */
@Entity
@DiscriminatorValue("WRK")
public class Worksheet extends Resource {}
