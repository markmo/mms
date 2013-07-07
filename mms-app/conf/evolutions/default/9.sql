# --- !Ups

insert into mms.settings(id, custom_schema, organization_id) values(1, '{
  "schema": {
    "metadataItemType": {
      "type": "string",
      "title": "Item Type",
      "required": true,
      "enum": ["Derived", "Input", "Matrix", "Compound"]
    },
    "registrationStatus": {
      "type": "string",
      "title": "Registration Status",
      "enum": ["Current", "Proposed", "Superseded"]
    },
    "representationalClass": {
      "type": "string",
      "title": "Representational Class",
      "enum": ["Percentage", "Rate", "Mean", "Count", "Average", "Code", "Text", "Date", "Identifier", "Sum", "Ratio", "Currency", "Quantity", "Time"]
    },
    "calculation": {
      "type": "string",
      "title": "Calculation"
    }
  },
  "form": [
    {
      "key": "metadataItemType"
    },
    {
      "key": "registrationStatus"
    },
    {
      "key": "representationalClass"
    },
    {
      "key": "calculation",
      "type": "textarea",
      "htmlClass": "wide"
    }
  ]
}', 1);

# --- !Downs

truncate table mms.settings cascade;
