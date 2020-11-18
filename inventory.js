$(document).ready(() => {
    google.charts.load('current', {packages: ['corechart', 'bar']});
    google.charts.setOnLoadCallback(() => {
        $.ajax({
            url: "AvailableProductsAPI",
            type: "POST",
            data: "{}",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (msg) {
                formatJsonAccordingToGoogleChart(msg)            
            },
            error: function(){
                console.log("error occurred while making ajax call;")
            }
        });
    });

    $('.gridDataTable').DataTable({
		pageLength : 5,
		lengthMenu: [[5, 10, -1], [5, 10, 'All']],
	});
})

//This method will parse json data and build datatable required by google api to plot the bar chart.
function formatJsonAccordingToGoogleChart(msg)
{
    var newMsg = JSON.stringify(msg);
    //alert(newMsg);
    //[{"productName":"Basic Plan","productPrice":"22.99","numberOfAvailableProducts":"9","productDiscount":"22.99","manufacturerRebate":"0"},{"productName":"LG FitnessWatch","productPrice":"399.99","numberOfAvailableProducts":"10","productDiscount":"399.99","manufacturerRebate":"0"},{"productName":"LG Headphone","productPrice":"20.99","numberOfAvailableProducts":"10","productDiscount":"20.99","manufacturerRebate":"0"},{"productName":"LG Laptop","productPrice":"689.99","numberOfAvailableProducts":"10","productDiscount":"689.99","manufacturerRebate":"0"},{"productName":"LG Phone","productPrice":"389.99","numberOfAvailableProducts":"10","productDiscount":"389.99","manufacturerRebate":"100"},{"productName":"LG SmartWatch","productPrice":"289.99","numberOfAvailableProducts":"10","productDiscount":"289.99","manufacturerRebate":"0"},{"productName":"LG SoundSystem","productPrice":"72.99","numberOfAvailableProducts":"10","productDiscount":"72.99","manufacturerRebate":"0"},{"productName":"LG Television","productPrice":"87.99","numberOfAvailableProducts":"10","productDiscount":"87.99","manufacturerRebate":"0"},{"productName":"LG VoiceAssistant","productPrice":"199.99","numberOfAvailableProducts":"10","productDiscount":"199.99","manufacturerRebate":"0"},{"productName":"Microsoft FitnessWatch","productPrice":"40.99","numberOfAvailableProducts":"10","productDiscount":"40.99","manufacturerRebate":"0"},{"productName":"Microsoft Headphone","productPrice":"149.99","numberOfAvailableProducts":"10","productDiscount":"149.99","manufacturerRebate":"0"},{"productName":"Microsoft Laptop","productPrice":"849.99","numberOfAvailableProducts":"10","productDiscount":"849.99","manufacturerRebate":"51"},{"productName":"Microsoft Phone","productPrice":"589.99","numberOfAvailableProducts":"10","productDiscount":"589.99","manufacturerRebate":"0"},{"productName":"Microsoft SmartWatch","productPrice":"389.99","numberOfAvailableProducts":"10","productDiscount":"389.99","manufacturerRebate":"0"},{"productName":"Microsoft SoundSystem","productPrice":"50.99","numberOfAvailableProducts":"10","productDiscount":"50.99","manufacturerRebate":"0"},{"productName":"Microsoft Television","productPrice":"150.49","numberOfAvailableProducts":"9","productDiscount":"150.49","manufacturerRebate":"10"},{"productName":"Microsoft VoiceAssistant","productPrice":"150.99","numberOfAvailableProducts":"10","productDiscount":"150.99","manufacturerRebate":"0"},{"productName":"Onida FitnessWatch","productPrice":"289.99","numberOfAvailableProducts":"10","productDiscount":"289.99","manufacturerRebate":"13"},{"productName":"Onida Headphone","productPrice":"89.99","numberOfAvailableProducts":"10","productDiscount":"89.99","manufacturerRebate":"0"},{"productName":"Onida Laptop","productPrice":"489.99","numberOfAvailableProducts":"10","productDiscount":"489.99","manufacturerRebate":"0"},{"productName":"Onida Phone","productPrice":"300.99","numberOfAvailableProducts":"10","productDiscount":"300.99","manufacturerRebate":"11"},{"productName":"Onida SmartWatch","productPrice":"149.99","numberOfAvailableProducts":"10","productDiscount":"149.99","manufacturerRebate":"0"},{"productName":"Onida SoundSystem","productPrice":"59.99","numberOfAvailableProducts":"10","productDiscount":"59.99","manufacturerRebate":"10"},{"productName":"Onida Television","productPrice":"75.99","numberOfAvailableProducts":"10","productDiscount":"75.99","manufacturerRebate":"0"},{"productName":"Onida VoiceAssistant","productPrice":"149.99","numberOfAvailableProducts":"10","productDiscount":"149.99","manufacturerRebate":"0"},{"productName":"Premium Plan","productPrice":"32.99","numberOfAvailableProducts":"10","productDiscount":"32.99","manufacturerRebate":"0"},{"productName":"Samsung FitnessWatch","productPrice":"499.99","numberOfAvailableProducts":"10","productDiscount":"499.99","manufacturerRebate":"0"},{"productName":"Samsung Headphone ","productPrice":"89.99","numberOfAvailableProducts":"10","productDiscount":"89.99","manufacturerRebate":"12"},{"productName":"Samsung Laptop","productPrice":"520.99","numberOfAvailableProducts":"10","productDiscount":"520.99","manufacturerRebate":"0"},{"productName":"Samsung Phone","productPrice":"489.99","numberOfAvailableProducts":"10","productDiscount":"489.99","manufacturerRebate":"0"},{"productName":"Samsung SmartWatch","productPrice":"280.99","numberOfAvailableProducts":"10","productDiscount":"280.99","manufacturerRebate":"10"},{"productName":"Samsung SoundSystem","productPrice":"200.99","numberOfAvailableProducts":"10","productDiscount":"200.99","manufacturerRebate":"0"},{"productName":"Samsung Television","productPrice":"99.99","numberOfAvailableProducts":"10","productDiscount":"99.99","manufacturerRebate":"5"},{"productName":"Samsung VoiceAssistant","productPrice":"50.99","numberOfAvailableProducts":"10","productDiscount":"50.99","manufacturerRebate":"1"},{"productName":"Sony FitnessWatch","productPrice":"289.99","numberOfAvailableProducts":"10","productDiscount":"289.99","manufacturerRebate":"11"},{"productName":"Sony Headphone","productPrice":"189.99","numberOfAvailableProducts":"10","productDiscount":"189.99","manufacturerRebate":"0"},{"productName":"Sony Laptop","productPrice":"549.99","numberOfAvailableProducts":"10","productDiscount":"549.99","manufacturerRebate":"0"},{"productName":"Sony Phone","productPrice":"189.99","numberOfAvailableProducts":"10","productDiscount":"189.99","manufacturerRebate":"13"},{"productName":"Sony SmartWatch","productPrice":"389.99","numberOfAvailableProducts":"10","productDiscount":"389.99","manufacturerRebate":"13"},{"productName":"Sony SoundSystem","productPrice":"112.99","numberOfAvailableProducts":"10","productDiscount":"112.99","manufacturerRebate":"0"},{"productName":"Sony Television","productPrice":"119.99","numberOfAvailableProducts":"10","productDiscount":"119.99","manufacturerRebate":"0"},{"productName":"Sony VoiceAssistant","productPrice":"149.99","numberOfAvailableProducts":"10","productDiscount":"149.99","manufacturerRebate":"0"},{"productName":"Ultimate Plan","productPrice":"42.99","numberOfAvailableProducts":"10","productDiscount":"42.99","manufacturerRebate":"2"}]
    var parsedData = $.parseJSON(newMsg);
    var data = new Array();

    data[0] = ["Product Name", "Number of available products"];

    for(var i = 0; i < parsedData.length; i++) {
        data[i + 1] = [parsedData[i].productName, parseInt(parsedData[i].numberOfAvailableProducts)];

    }
    console.log(data);
    drawChart(data);
}

//Plot the chart using 2d array and product names as subtitles;
function drawChart(data)
{

    //Invoke google's built in method to get data table object required by google.
    let chartData = google.visualization.arrayToDataTable(data);

    var options = {
        title: 'Total Products Available',
        bars: 'horizontal', // Required for Material Bar Charts.
        chartArea: {'height': '90%'},
        legend: {'position': 'bottom'}
    };

    var chart = new google.visualization.BarChart(document.getElementById('chartDivNumberOfAvailableProducts'));
    chart.draw(chartData, options);
}