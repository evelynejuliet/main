package seedu.address.logic.commands.templateList.template;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TemplateList;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.TemplateItem;
import seedu.address.testutil.TemplateItemBuilder;

public class AddTemplateItemCommandTest {

    @Test
    public void constructor_nullTemplateItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTemplateItemCommand(null));
    }

    @Test
    public void execute_templateItemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTemplateItemAdded modelStub = new ModelStubAcceptingTemplateItemAdded();
        TemplateItem validTemplateItem = new TemplateItemBuilder().build();

        CommandResult commandResult = new AddTemplateItemCommand(validTemplateItem).execute(modelStub);

        //assertEquals(String.format(AddTemplateItemCommand.MESSAGE_SUCCESS, validTemplateItem),
        //commandResult.getFeedbackToUser());
        //assertEquals(Arrays.asList(validTemplateItem), modelStub.templateItemsAdded);
    }

    @Test
    public void equals() {
        TemplateItem mincedMeat = new TemplateItemBuilder().withName("Ground Pork").build();
        TemplateItem freshVeg = new TemplateItemBuilder().withName("Tomato").build();
        AddTemplateItemCommand addMincedMeatCommand = new AddTemplateItemCommand(mincedMeat);
        AddTemplateItemCommand addFreshVegCommand = new AddTemplateItemCommand(freshVeg);

        // same object -> returns true
        assertTrue(addMincedMeatCommand.equals(addMincedMeatCommand));

        // same values -> returns true
        AddTemplateItemCommand addMincedMeatCommandCopy = new AddTemplateItemCommand(mincedMeat);
        assertTrue(addMincedMeatCommand.equals(addMincedMeatCommandCopy));

        // different types -> returns false
        assertFalse(addMincedMeatCommand.equals(1));

        // null -> returns false
        assertFalse(addMincedMeatCommand.equals(null));

        // different person -> returns false
        assertFalse(addMincedMeatCommand.equals(addFreshVegCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroceryItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryList(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getGroceryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroceryItem(GroceryItem food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroceryItem(GroceryItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroceryItem(GroceryItem target, GroceryItem editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GroceryItem> getFilteredGroceryItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroceryItemList(Predicate<GroceryItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithTemplateItem extends ModelStub {
        private final TemplateItem templateItem;

        ModelStubWithTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            this.templateItem = templateItem;
        }

        public boolean hasTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            return this.templateItem.isSameFood(templateItem);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingTemplateItemAdded extends ModelStub {
        final ArrayList<TemplateItem> templateItemsAdded = new ArrayList<>();

        public boolean hasTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            return templateItemsAdded.stream().anyMatch(templateItem::isSameFood);
        }

        public void addTemplateItem(TemplateItem templateItem) {
            requireNonNull(templateItem);
            templateItemsAdded.add(templateItem);
        }

        public ReadOnlyTemplateList getTemplateList() {
            return new TemplateList();
        }
    }

}