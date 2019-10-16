package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.TemplateList;
import seedu.address.model.food.UniqueTemplateItems;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "templatelist")
class JsonSerializableTemplateList {

    public static final String MESSAGE_DUPLICATE_TEMPLATE = "TemplateList contains duplicate template(s).";

    private final List<JsonAdaptedTemplate> templateList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTemplateList} with the given template items.
     */
    @JsonCreator
    public JsonSerializableTemplateList(@JsonProperty("templateList") List<JsonAdaptedTemplate> templates) {
        this.templateList.addAll(templates);
    }

    /**
     * Converts a given {@code ReadOnlyTemplateList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTemplateList}.
     */
    public JsonSerializableTemplateList(ReadOnlyTemplateList source) {
        templateList.addAll(source.getTemplateList().stream().map(JsonAdaptedTemplate::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this template list into the model's {@code TemplateList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TemplateList toModelType() throws IllegalValueException {
        TemplateList templates = new TemplateList();
        for (JsonAdaptedTemplate jsonAdaptedTemplate : templateList) {
            UniqueTemplateItems template = jsonAdaptedTemplate.toModelType();
            if (templates.hasTemplate(template)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEMPLATE);
            }
            templates.addTemplate(template);
        }
        return templates;
    }

}