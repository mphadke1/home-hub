$(function() {
    console.log("FURNITURE");

    $("#worker-select").change(function() {
        if($("#worker-select").val() === "manual") {
            $(".select-worker").removeClass("d-none");
        } else {
            $(".select-worker").addClass("d-none");
        }
    })
})