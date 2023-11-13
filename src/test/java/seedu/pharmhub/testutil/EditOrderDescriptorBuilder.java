package seedu.pharmhub.testutil;

import seedu.pharmhub.logic.commands.UpdateStatusCommand.EditOrderDescriptor;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.Status;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setOrderNumber(order.getOrderNumber());
        descriptor.setPerson(order.getPerson());
        descriptor.setMedicines(order.getMedicines());
        descriptor.setStatus(order.getStatus());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new Status(Status.toOrderStatus(status)));
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
