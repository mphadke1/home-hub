let map;
let geocoder;

function initMap() {
    let chicagoLatLong = { lat: 41.878113, lng: -87.739 };
    map = new google.maps.Map(document.getElementById("map"), {
        center: chicagoLatLong,
        zoom: 9,
    });

    geocoder = new google.maps.Geocoder();
}

$(document).ready(() => {
    $("#buy-button").click((e) => {
        e.preventDefault(); 

        $.ajax({
            url: "HeatMap",
            type: "POST",
            data: { type: $("#type").val() },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (msg) {
                let newMsg = JSON.stringify(msg);
                let json = $.parseJSON(newMsg);

                let heatMapData = [];
                let promiseList = [];
                for (let i = 0; i < json.length; i++) {
                    const store = json[i];
                    let address = store.street + ", " + store.city + ", " + store.state + ", " + store.zipcode;
                    let promise = getLatLong(address).then(latlon => {
                        if(latlon != false)
                            heatMapData.push({location: new google.maps.LatLng(latlon.lat, latlon.lng), weight: store.weight});
                    });
                    promiseList.push(promise);
                }

                Promise.all(promiseList).then(() => {
    
                    let heatmap = new google.maps.visualization.HeatmapLayer({
                        data: heatMapData,
                        radius: 70
                    });
                    heatmap.setMap(map);
                })

            },
            error: function () {
            }
        });
    });
})

function getLatLong(address) {
    return new Promise((resolve, reject) => {
        var apikey = "AIzaSyCW5bEaU5FDZwF2OvZIGzRwICVbbpCQRPE";
        var query = 'https://maps.googleapis.com/maps/api/geocode/json?address=' + address + '&key=' + apikey;
        $.getJSON(query, function (data) {
            if (data.status === 'OK') {
                resolve(data.results[0].geometry.location);
            } else {
                console.error("Error in geocode: ", data.status);
                reject(data.status);
            }
        })
    })
}