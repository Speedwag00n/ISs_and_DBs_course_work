function validateRequestForm(form) {
    let name = form.name.value;
	let description = form.description.value;
	let select
	if (form.select) {
        select = form.select.value;
        let object;
        if (form.objectId.length || form.objectId.checked) {
            object = form.objectId.value;
        }
        let service;
        if (form.serviceId.length || form.serviceId.checked) {
            service = form.serviceId.value;
        }
	}

    let valid = true;

	if (!(isPresented(name, "Name", "name"))) {
		valid = false;
	} else if (name.length < 5) {
        showWarning("Name must contain at least 5 symbols", "name");
		valid = false;
	}

    if (select) {
        if (select == "selectObject") {
            if (!(isPresented(object, "Object", "object"))) {
                valid = false;
            }
        } else if (select == "selectService") {
            if (!(isPresented(service, "Service", "service"))) {
                valid = false;
            }
        }
    }

    return valid
}