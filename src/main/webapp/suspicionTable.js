<table id="personDataTable">
    <tr>
       <th>S.No.</th>
        <th>Customer Name</th>
        <th>Suspicion Index</th>
        <th>Suspicion Reason</th>
    </tr>
</table>
<script>
// ignore this first line (its fidle mock) and it will return what ever you pass as json:... parameter... consider to change it to your ajax call
$.ajax({
    url: '/echo/json/',
    type: "post",
    dataType: "json",
    data: {
        json: JSON.stringify( [{
		"overallSuspicionIndex": 0.25,
		"customerId": null,
		"customerName": "Rajat Sharma",
		"suspicionReason": "BANK-(Aman Khanna),PHONENUMBER-(Aman Khanna)"
	}, {
		"overallSuspicionIndex": 0.25,
		"customerId": null,
		"customerName": "Aman Khanna",
		"suspicionReason": "BANK-(Rajat Sharma),PHONENUMBER-(Rajat Sharma)"
	}]),
        delay: 3
    },
    success: function(data, textStatus, jqXHR) {
        // since we are using jQuery, you don't need to parse response
        drawTable(data);
    }
});

function drawTable(data) {
    for (var i = 0; i < data.length; i++) {
        drawRow(i+1,data[i]);
    }
}

function drawRow(id,rowData) {
    var row = $("<tr />")
    $("#personDataTable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
     row.append($("<td>" + id + "</td>"));
    row.append($("<td>" + rowData.customerName + "</td>"));
    row.append($("<td>" + (rowData.overallSuspicionIndex)*100 + "%" +"</td>"));
    row.append($("<td>" + rowData.suspicionReason + "</td>"));
}
</script>
