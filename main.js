$( function() {
    var Services = [
      "Home Cleaning",
      "Furniture Assembly",
      "Handyman Services",
      "Hanging Pictures & Shelves",
      "Office Cleaning",
      "TV Mounting",
      "Home Theater AV Setup",
      "Bed Assembly",
      "Office Furniture Assembly",
      "Faucet Replacement",
      "Toilet Trouble",
      "Plumbing Services",
      "Outlet Installation",
      "Light Fixtures",
      "Electrical Service",
      "Interior Painting",
      "Bedroom Painting",
      "Accent Wall Painting",
      "Moving Help",
      "Smart Home Hub Setup",
      "Smart Security Cam Installation",
      "Smart Device Installation"
    ];
    $( "#search" ).autocomplete({
      source: Services
    });
  } );


// $(function () {

// 	$('.list-group-item').on('click', function () {
// 		$('.fas', this)
// 		.toggleClass('fa-chevron-right')
// 		.toggleClass('fa-chevron-down');
// 	});

// 	$('#pickupType').change(function () {
// 		if($('#pickupType').val() == 'Store Pickup') {
// 			$('#storeList').removeClass('d-none');
// 			$('#storeId').prop('required', true);
// 		} else {
// 			$('#storeList').addClass('d-none');
// 			$('#storeId').prop('required', false);
// 		}
// 	})

// 	$('#search').autocomplete({
// 		source: (request, response) => {
// 			console.log(request.term);
// 			$.ajax({
// 				url: "autocomplete",
// 				type: "GET",
// 				data: {action: "complete", searchId: request.term},
// 				contentType: "application/x-www-form-urlencoded; charset=utf-8",
// 				dataType: "json",
// 				success: function(res) {
// 					console.log("HERE");
// 					console.log(res);
// 					let array = [];
// 					for (let i = 0; i < res.length; i++) {
// 						const product = res[i];
// 						array.push({label: product.name, value: product.id});
// 					}
// 					response(array);
// 				}
// 			})
// 		},
// 		select: (event, ui) => {
// 			console.log(ui.item.value);
// 			$(location).attr("href", "ProductData?searchId=" + ui.item.value);
// 		}
// 	})
// });