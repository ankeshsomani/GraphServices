
//jQuery time
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches

$(document).ready(function () {

$(".next").click(function(){
	if(animating) return false;
	animating = true;
	
	current_fs = $(this).parent();
	next_fs = $(this).parent().next();
	
	//activate next step on progressbar using the index of next_fs
	$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
	
	//show the next fieldset
	next_fs.show(); 
	//hide the current fieldset with style
	current_fs.animate({opacity: 0}, {
		step: function(now, mx) {
			//as the opacity of current_fs reduces to 0 - stored in "now"
			//1. scale current_fs down to 80%
			scale = 1 - (1 - now) * 0.2;
			//2. bring next_fs from the right(50%)
			left = (now * 50)+"%";
			//3. increase opacity of next_fs to 1 as it moves in
			opacity = 1 - now;
			current_fs.css({
        'transform': 'scale('+scale+')',
        'position': 'absolute'
      });
			next_fs.css({'left': left, 'opacity': opacity});
		}, 
		duration: 800, 
		complete: function(){
			current_fs.hide();
			animating = false;
		}, 
		//this comes from the custom easing plugin
		easing: 'easeInOutBack'
	});
});

$(".previous").click(function(){
	if(animating) return false;
	animating = true;
	
	current_fs = $(this).parent();
	previous_fs = $(this).parent().prev();
	
	//de-activate current step on progressbar
	$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
	
	//show the previous fieldset
	previous_fs.show(); 
	//hide the current fieldset with style
	current_fs.animate({opacity: 0}, {
		step: function(now, mx) {
			//as the opacity of current_fs reduces to 0 - stored in "now"
			//1. scale previous_fs from 80% to 100%
			scale = 0.8 + (1 - now) * 0.2;
			//2. take current_fs to the right(50%) - from 0%
			left = ((1-now) * 50)+"%";
			//3. increase opacity of previous_fs to 1 as it moves in
			opacity = 1 - now;
			current_fs.css({'left': left});
			previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
		}, 
		duration: 800, 
		complete: function(){
			current_fs.hide();
			animating = false;
		}, 
		//this comes from the custom easing plugin
		easing: 'easeInOutBack'
	});
});

$(".submit").click(function(){
	return false;
})


//var customerElement = {
//    "title": "Mr",
//    "firstName": firstname,
//    "lastName": lastname,
//    "dateOfBirth": dob,
//    "postcode": postcode,
//    "addressLine1": address1,
//    "addressLine2": address2,
//    "town": town,
//    "county": county,
//    "email": email,
//    "homeNumber": home,
//    "mobileNumber": mobile,
//    "status": "Married",

//    "nameOfEmployer": employee,
//    "monthsAtEmployment": employment,
//    "bankName": bankname,
//    "bankIbanCode": bankcode,
//    "bankAccountNumber": accountnumber,
//    "monthsWithBank": monthwithbank,
//    "loanRequested": loanrequested
//}


    //var customerCollection = {};
    //var customers = [];
    //customerCollection.customers = customers;

    //var customerElement = {
    //    title: "Mr",
    //    firstName: firstname,
    //    lastName: lastname,
    //    dateOfBirth: dob,
    //    postcode: postcode,
    //    addressLine1: address1,
    //    addressLine2: address2,
    //    town: town,
    //    county: county,
    //    email: email,
    //    homeNumber: home,
    //    mobileNumber: mobile,
    //    status: "Married",

    //    nameOfEmployer: employee,
    //    monthsAtEmployment: employment,
    //    bankName: bankname,
    //    bankIbanCode: bankcode,
    //    bankAccountNumber: accountnumber,
    //    monthsWithBank: monthwithbank,
    //    loanRequested: loanrequested
    //};

    //customerCollection.customers.push(customerElement);                

    jQuery.ajax(
    {
        type: 'POST',
        url : "http://localhost:8085/GraphServices/test",                    
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(customerCollection),

        success: function (data) {
            alert("Success");
            location.reload();                        
        },
        error: function (data) {
            alert("fail");                      
        }
    });


    /*
            var parameters = {};
            parameters.url = "http://localhost:58443/API/CUSTOMER";
            parameters.type = "POST";

            //parameters.data = strFinal;
            parameters.data = JSON.stringify(objCustomer);

            parameters.dataType = "json";
            parameters.contentType = "application/json; charset=utf-8";
            parameters.success = function () { alert("Success"); };
            parameters.error = function () { alert("Error"); };

            $.ajax(parameters);
    */

    ////////////////////////////////////////////////////////


    var xhttp;
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
    } else {
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xhttp.open("POST", "http://localhost:58443/API/CUSTOMER", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("firstName='Arvind'");

    //////////////////////////////////////////////////////////////////////
    //$.jquery()
});