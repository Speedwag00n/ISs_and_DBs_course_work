function validateSuggestionForm(form) {
	let name = form.name.value;
	let description = form.description.value;
	let select = form.select.value;
    let object;
    if (form.object.length || form.object.checked) {
        object = form.object.value;
    }
    let service;
    if (form.service.length || form.service.checked) {
        service = form.service.value;
    }

    let valid = true;

	if (!(isPresented(name, "Name", "name"))) {
		valid = false;
	} else if (name.length < 5) {
        showWarning("Name must contain at least 5 symbols", "name");
        valid = false;
    }

    if (select == "selectObject") {
    	if (!(isPresented(object, "Object", "object"))) {
    		valid = false;
    	}
    } else if (select == "selectService") {
        if (!(isPresented(service, "Service", "service"))) {
            valid = false;
        }
    }

    return valid
}