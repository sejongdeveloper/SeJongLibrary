/**
 *  autocomplate.js : autocomplete를 수행하는 스크립트
 */

let autocomplete = (function () { 
	
	let _inp = null; 
	
	let _arr = []; 
	
	let _currentFocus; 
	
	let _func = null;
	
	let _setAutoComplete = function (inp, arr) { 
		// autocomplete할 배열 
		_arr = arr; 
		
		// 기존의 input 값과 같지 않다면, 리스너 해제 
		if (_inp === inp) { 
			return; 
		}
		// 기존 리스너해제 
		_removeListener(); 
		
		// 새로운 input 의 리스너 추가. 
		_inp = inp; 
		_inp.addEventListener("input", inputEvent); 
		_inp.addEventListener("keydown", keydownEvent); 
	} 
	
	let inputEvent = function (e) { 
		var a, b, i, val = this.value; 
		
		// 이전 생성된 div 제거 
		closeAllLists(); 
		
		// 요소 확인 
		if (!val) {
			return false; 
		} 
		
		// 현재의 포커스의 위치는 없음. 
		_currentFocus = -1; 
		
		// autocomplet에서 항목을 보여줄 div 생성 
		a = document.createElement("DIV"); 		
		//
		a.setAttribute("id", this.id + "autocomplete-list"); 
		// css 적용 
		a.setAttribute("class", "autocomplete-items"); 
		
		// input 아래의 div 붙이기. 
		this.parentNode.appendChild(a); 
		// autocomplet할 요소 찾기 
		for (i = 0; i < _arr.length; i++) { 
			// 배열의 요소를 현재 input의 value의 값이 포함된다면 추가
			if (_arr[i].toUpperCase().indexOf(val.toUpperCase()) > -1) { 
				b = document.createElement("DIV");			 
	
				var searchWord = _arr[i].indexOf(val);				// 배열에서 검색 값의 위치
				var word1 = _arr[i].substr(0, searchWord);			// 검색 값 이전 단어
				var word2 = _arr[i].substr(searchWord, val.length); // 검색 값
				var word12 = word1 + word2;
				var word3 = _arr[i].substr(word12.length);			// 검색 값 이후 단어
				
				// value의 값만 굵게 표시
				b.innerHTML = "<strong>" + '<span style="font-weight: normal;">'+word1+'</span>' + word2 + "</strong>"; 
				b.innerHTML += word3; 
				b.innerHTML += "<input type='hidden' value='" + _arr[i] + "'>"; 
				
				// 생성된 div에서 이벤트 발생시 hidden으로 생성된 input안의 value의 값을 autocomplete할 요소에 넣기 
				
				b.addEventListener("click", function (e) { 
					_inp.value = this.getElementsByTagName("input")[0].value; 
					closeAllLists(); 
				}); 
				
				// autocomplete 리스트를 붙이기. 
				a.appendChild(b); 
				
			} 
		}
		
		
	} 
	
	let keydownEvent = function (e) { 
		
		var x = document.getElementById(this.id + "autocomplete-list"); 
		// 선택할 요소 없으면 null , 
		/*
		 * <div id="autoInputautocomplete-list" class="autocomplete-items">
		 * 		<div class="autocomplete-active">
		 * 			<strong>A</strong>ardvark<input type="hidden" value="Aardvark">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>lbatross<input type="hidden" value="Albatross">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>lligator<input type="hidden" value="Alligator">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>lpaca<input type="hidden" value="Alpaca">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>nt<input type="hidden" value="Ant">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>nteater<input type="hidden" value="Anteater">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>ntelope<input type="hidden" value="Antelope">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>pe<input type="hidden" value="Ape">
		 * 		</div>
		 * 		<div>
		 * 			<strong>A</strong>rmadillo<input type="hidden" value="Armadillo">
		 * 		</div>
		 * </div>
		*/ 
		if (x) { 
			// x안에 속한 div의 값을 htmlCollection의 값으로 받아옴. 
			x = x.getElementsByTagName("div"); 
		} 
		
		if (e.keyCode == 40) { 
			// down 
			// 현재위치 증가 
			_currentFocus++; 
			// 현재위치의 포커스 나타내기 
			addActive(x); 
		} else if (e.keyCode == 38) {
			// up 
			// 현재위치 감소 
			_currentFocus--; 
			// 현재위치의 포커스 나타내기 
			addActive(x); 
		} else if (e.keyCode == 13) { 
			// enter 
			e.preventDefault(); 
			// 현재위치가 아이템 선택창내에 있는 경우 
			if (_currentFocus > -1) { 
				// 현재 위치의 값 클릭 
				if (x) {
					x[_currentFocus].click(); 
					
					if (_func !== undefined) {
						_func(this.value);
					}
				} // if (x) end
			} // if (_currentFocus > -1) end 
		} // else if (e.keyCode == 13) end
	} // keydownEvent() end
	
	// 자동완성 선택
	let addActive = function (x) { 
		if (!x) return false; 
		removeActive(x); 
		if (_currentFocus >= x.length) _currentFocus = 0; 
		if (_currentFocus < 0) _currentFocus = (x.length - 1); 
		x[_currentFocus].classList.add("autocomplete-active"); 
	} 
	
	// 자동완성 리스트 초기화
	let removeActive = function (x) { 
		for (var i = 0; i < x.length; i++) { 
			x[i].classList.remove("autocomplete-active"); 
		} 
	} 
	
	// 자동완성 초기화
	let closeAllLists = function (elmnt) { 
		var x = document.getElementsByClassName("autocomplete-items"); 
		for (var i = 0; i < x.length; i++) { 
			if (elmnt != x[i] && elmnt != _inp) { 
				x[i].parentNode.removeChild(x[i]); 
			} 
		} 
	} 
	
	// 리스너 초기화
	let _removeListener = function () { 
		if (_inp !== null) { 
			_inp.removeEventListener("input", inputEvent, false); 
			_inp.removeEventListener("keydown", keydownEvent, false); 
		} 
	} 
	
	// 초기설정
	return { 
		setAutoComplete: function (inp, arr, func) { 
			_setAutoComplete(inp, arr); 
			_func = func;
		} 
	} 
	
})();
			
		
		

