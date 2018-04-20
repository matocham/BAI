function bindLogoutButton(){
    var logoutButton = $("#logout");
    if(logoutButton.length){
        logoutButton.on("click", function(event){
            $("#logoutForm").submit();
            event.preventDefault();
        })
    }
}

function setUpPasswordStep(){
	$("input[type='password']").on('keyup', function(ev){
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
	setTimeout(function(){$("#secondForm input[type=password]").first().focus();}, 500)
	
}