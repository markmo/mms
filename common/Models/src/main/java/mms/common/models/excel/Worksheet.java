package mms.common.models.excel;

import javax.persistence.*;

import mms.common.models.Resource;

/**
 * User: markmo
 * Date: 27/02/13
 * Time: 9:32 PM
 */
@Entity
@DiscriminatorValue("WRK")
public class Worksheet extends Resource {}
