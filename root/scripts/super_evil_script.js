function do_evil() {
	// if you are reading this, hi!
	let evil_div = document.querySelector(".evil_div");
	let evil_output = document.querySelector(".evil_message");
	let evil_inputs = evil_div.querySelectorAll("input");
	let evil_name = evil_inputs[0].value.trim();
	let evil_priority = +evil_inputs[1].value;
	if (evil_name == "") return evil_output.innerText = "Name must not be blank!";
	if (evil_priority < 1 || evil_priority > 5) return evil_output.innerText = "Priority must be between 1 and 5!";
	evil_output.innerText = "";
	evil_div.classList.add("loading");
	fetch("/super_evil_endpoint", {
		method: "POST",
		headers: {
			"Content-Type": "text/plain" // do not change this
		},
		body: `evil_name: ${evil_name}\nevil_priority: ${evil_priority}`
	})
	.then(evil_promise => evil_promise.text())
	.then(evil_result => {
		evil_output.innerText = evil_result;
		evil_div.classList.remove("loading");
	});
	
	
}