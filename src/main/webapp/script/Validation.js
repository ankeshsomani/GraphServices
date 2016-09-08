// Wait for the DOM to be ready

$(function () {
    // Initialize form validation on the registration form.
    // It has the name attribute "registration"
    $("form[name='registration']").validate({
        // Specify validation rules
        rules: {
            // The key name on the left side is the name attribute
            // of an input field. Validation rules are defined
            // on the right side
            firstname: "required",
            lastname: "required",
            dob: "required",
            postcode: "required",
            address1: "required",
            address2: "required",
            town: "required",
            country: "required",
            email: "required",
            home: "required",
            mobile: "required"

            //email: {
            //    required: true,
            //    // Specify that email should be validated
            //    // by the built-in "email" rule
            //    email: true
            //},
            //password: {
            //    required: true,
            //    minlength: 5
            //}
        },


        // Specify validation error messages
        messages: {
            firstname: "Please enter your firstname",
            lastname: "Please enter your lastname",
            dob: "Please enter your dob",
            postcode: "Please enter your postcode",
            address1: "Please enter your address1",
            address2: "Please enter your address2",
            town: "Please enter your town",
            country: "Please enter your country",
            email: "Please enter your email",
            home: "Please enter your home",
            mobile: "Please enter your mobile"

            //password: {
            //    required: "Please provide a password",
            //    minlength: "Your password must be at least 5 characters long"
            //},
            //email: "Please enter a valid email address"
        },


        // Make sure the form is submitted to the destination defined
        // in the "action" attribute of the form when valid
        submitHandler: function (form) {
            form.submit();
        }
    });
});