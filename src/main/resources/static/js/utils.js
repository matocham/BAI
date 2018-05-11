function bindLogoutButton(){
    var logoutButton = $("#logout");
    if(logoutButton.length){
        logoutButton.on("click", function(event){
            $("#logoutForm").submit();
            event.preventDefault();
        })
    }
}

special_chars = [8,9,13,16,17,18,19,20,27,33,34,35,36,37,38,39,40,45,46,91,92,93,112,113,114,115,116,117,118,119,120,121,122,123,144,145]
function setUpPasswordStep(){
	$("input[type='password']").on('keyup', function(ev){
		if(special_chars.includes(ev.keyCode)){
			return
		}
		 $next = $(ev.target).nextAll("input[type='password']:first")
		 while($next.prop('disabled') && $next.length > 0){
			 $next = $next.nextAll("input[type='password']:first")
		 }
		 if($next.length){
			 $next.focus()
		 }
	})

	$("input[type='password']").on('focus', function(ev){
		ev.target.select()
	})
	setTimeout(function(){$("#secondForm input[type=password]").first().focus();}, 200)
	
}