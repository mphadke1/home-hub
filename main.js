console.log("SUCCESS")
$(function() {
        
    $('.list-group-item').on('click', function() {
      $('.fas', this)
        .toggleClass('fa-chevron-right')
        .toggleClass('fa-chevron-down');
    });
  
});