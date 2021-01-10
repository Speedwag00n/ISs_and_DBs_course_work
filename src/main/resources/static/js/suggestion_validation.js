function validateSuggestionForm(form) {
    let valid = true;

	let name = form.name.value;
	let description = form.description.value;
	let select = form.select.value;
	if (select == "selectObject") {
        let object;
        if (!form.object) {
            showWarning("You don't have any object in stock", "object");
            valid = false;
        } else if (form.object.length) {
            object = form.object.value;
            if (!(isPresented(object, "Object", "object"))) {
                valid = false;
            }
        } else if (!form.object.checked) {
            showWarning("Field 'Object' can't be blank", "object");
            valid = false;
        }
    }
    if (select == "selectService") {
        let service;
        if (!form.service) {
            showWarning("You don't have any created service", "service");
            valid = false;
        } else if (form.service.length) {
            service = form.service.value;
            if (!(isPresented(service, "Service", "service"))) {
                valid = false;
            }
        } else if (!form.service.checked) {
            showWarning("Field 'Service' can't be blank", "service");
            valid = false;
        }
    }

	if (!(isPresented(name, "Name", "name"))) {
		valid = false;
	} else if (name.length < 5) {
        showWarning("Name must contain at least 5 symbols", "name");
        valid = false;
    }

    return valid
}