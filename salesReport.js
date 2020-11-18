$(document).ready(() => {
    google.charts.load('current', {packages: ['corechart', 'bar']});
    google.charts.setOnLoadCallback(() => {
        $.ajax({
            url: "TotalProductSalesAPI",
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

function formatJsonAccordingToGoogleChart(msg)
{
    var newMsg = JSON.stringify(msg);
    var parsedData = $.parseJSON(newMsg);
    
    var data = new Array();

    data[0] = ["Product Name", "Total Sales for Product"];

    for(var i = 0; i < parsedData.length; i++) {
        data[i + 1] = [parsedData[i].productName, parseFloat(parsedData[i].productTotalSales)];
    }
    
    drawChart(data);
}

//Plot the chart using 2d array and product names as subtitles;
function drawChart(data)
{
    console.log(data);
    //Invoke google's built in method to get data table object required by google.
    var chartData = google.visualization.arrayToDataTable(data);

    var options = {
        title: 'Total Product Sales',
        bars: 'horizontal', // Required for Material Bar Charts.
        chartArea: {'height': '90%'},
        legend: {'position': 'none'}
    };

    var chart = new google.visualization.BarChart(document.getElementById('chartDivTotalProductSales')); //chartDivNumberOfAvailableProducts chart_div
    chart.draw(chartData, options);
}