function bindLogoutButton(){
    var logoutButton = $("#logout");
    if(logoutButton.length){
        logoutButton.on("click", function(event){
            $("#logoutForm").submit();
            event.preventDefault();
        })
    }
}