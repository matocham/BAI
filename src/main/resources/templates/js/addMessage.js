
function searchAjax() {

    $("#addItemForm button.addProductButton").click(function(e) {
        var message =  $("#importFromSalesForce input#message").val();
        $.ajax({
            type : "GET",
            url : "addMessageToDB?message=" + message
        });
    });
};