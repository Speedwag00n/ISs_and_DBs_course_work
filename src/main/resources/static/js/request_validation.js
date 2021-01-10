function validateRequestForm(form) {
    let valid = true;

    let name = form.name.value;
	let description = form.description.value;
	let select;
	if (form.select) {
	    select = form.select.value;
	    if (select == "selectObject") {
            let object;
            if (!form.objectId) {
                    showWarning("You don't have any object in stock", "object");
                    valid = false;
            } else if (form.objectId.length) {
                object = form.objectId.value;
                if (!(isPresented(object, "Object", "object"))) {
                    valid = false;
                }
            } else if (!form.objectId.checked) {
                showWarning("Field 'Object' can't be blank", "object");
                valid = false;
            }
        }
        if (select == "selectService") {
            let service;
            if (!form.serviceId) {
                showWarning("You don't have any created service", "service");
                valid = false;
            } else if (form.serviceId.length) {
                service = form.serviceId.value;
                if (!(isPresented(service, "Service", "service"))) {
                    valid = false;
                }
            } else if (!form.serviceId.checked) {
                showWarning("Field 'Service' can't be blank", "service");
                valid = false;
            }
        }
	}

	if (!(isPresented(name, "Name", "name"))) {
		valid = false;
	} else if (name.length < 6) {
        showWarning("Name must contain at least 6 symbols", "name");
		valid = false;
	}

    return valid
}