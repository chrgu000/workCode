(function(c) {
	c.alert = c.dialog.alert = function(b, a) {
		return c.dialog({
			id: "Alert",
			fixed: !0,
			lock: !0,
			content: b,
			ok: !0,
			beforeunload: a
		})
	};
	c.confirm = c.dialog.confirm = function(b, a, m, t) {
		return c.dialog({
			id: "Confirm",
			fixed: !0,
			lock: !0,
			content: b,
			ok: a,
			cancel: m,
			title: t
		})
	};
	c.prompt = c.dialog.prompt = function(b, a, m) {
		var d;
		return c.dialog({
			id: "Prompt",
			fixed: !0,
			lock: !0,
			content: ['<div style="margin-bottom:5px;font-size:12px">', b, '</div><div><input type="text" class="d-input-text" value="', m || "", '" style="width:18em;padding:6px 4px" /></div>'].join(""),
			initialize: function() {
				d = this.dom.content.find(".d-input-text")[0];
				d.select();
				d.focus()
			},
			ok: function() {
				return a && a.call(this, d.value)
			},
			cancel: function() {}
		})
	};
	c.load = c.dialog.load = function(option) {
		url = option.content;
		ok=option.ok;
		cancel=option.cancel
		var d =  c.dialog({
			id: option.id ,
			title:option.title || '信息',
	      	fixed: option.fixed || true,
	         lock: option.lock || true,
	        padding:option.padding || '10px',
	        follow:option.follow,
	        ok: option.ok,
	        okValue: option.okValue || '确定',
	        cancel: option.cancel,
	        cancelValue: option.cancelValue || '取消',
	        initialize:option.initialize,
	        beforeunload:option.beforeunload,
			width: option.width || 300,
			height: option.height || 200
		});
		if ( typeof option.before =='function' ){
			option.before(d);
		}
		$.get(option.content,function(data){
			d.content(data);
			$(document).append($('.javascript').html());
			if ( typeof option.after =='function' ){
				option.after(d,data);
			}
		});
        return d;
	};
	c.open = c.dialog.open = function(option) {
		option.width = option.width?option.width:300;
		option.height = option.height?option.height:300;
		option.content_id = option.content_id?option.content_id:'myiframe';
		option.scrolling = !option.scrolling?'no':option.scrolling;
		var d =  c.dialog({
			id: option.id ,
			title:option.title || '信息',
	      	fixed: option.fixed || true,
	        lock: option.lock || true,
			width:option.width,
			height:option.height,
	        padding:option.padding || '10px',
	        content: '<iframe id="'+option.content_id+'" width="'+option.width+'" height="'+option.height+'" src="'+option.content+'" scrolling="'+option.scrolling+'" frameborder="0" style="border:0;"></iframe>',
	        ok: option.ok,
	        okValue: option.okValue || '确定',
	        cancel: option.cancel,
	        cancelValue: option.cancelValue || '取消',
	        follow:option.follow
		})
        return d;
	};
	c.show = c.dialog.show = function(option) {
		var d =  c.dialog({
			id: option.id ,
			title:option.title ,
			width:option.width,
			height:option.height,
	        lock: option.lock || true,
	        padding:option.padding || '10px',
	        content: document.getElementById(option.content) ,
	   		ok: option.ok,
	        okValue: option.okValue ,
	        cancel: option.cancel,
	        cancelValue: option.cancelValue,
	        follow:option.follow,
			initialize:option.initialize
		})
        return d;
	};
	c.tips = c.dialog.tips = function(option) {
		var tipsType;
		switch(option.type){
			case 0:
			tipsType = 'tip_fail'
			break;
			case '0':
			tipsType = 'tip_fail'
			break;
			case 2:
			tipsType = 'tip_warning'
			break;
			case '2':
			tipsType = 'tip_warning'
			break;
			default:
			tipsType = 'tip_success'
			break;
		}
		return c.dialog({
			title:false,
			cancel:false,
			padding:'5px',
			follow:option.follow || false,
			fixed: option.fixed || true,
			lock: option.lock || false,
			initialize:option.initialize,
	        beforeunload:function(){
				var _educallback = option.callback;	
				if(_educallback!=''){
					if(typeof _educallback == 'function'){
						_educallback();
					}else{
						eval(_educallback);
					}
				}
	        }
		})
		.content('<div class="pub-tips-box"><div class="tips-box-txt"><i class="icon_'+tipsType+'"></i>'+option.content+'</div></div>')
	    .time(option.time || 1550);
	};
	//让取消铵钮隐藏的方法
	c.display = c.dialog.display = function(){
		$(this).find('.d-footer').hide();
	};
	c.dialog.prototype.shake = function() {
		var b = function(a, b, c) {
			var h = +new Date,
				e = setInterval(function() {
					var f = (+new Date - h) / c;
					1 <= f ? (clearInterval(e), b(f)) : a(f)
				}, 13)
		}, a = function(c, d, g, h) {
			var e = h;
			void 0 === e && (e = 6, g /= e);
			var f = parseInt(c.style.marginLeft) || 0;
			b(function(a) {
				c.style.marginLeft = f + (d - f) * a + "px"
			}, function() {
				0 !== e && a(c, 1 === e ? 0 : 1.3 * (d / e - d), g, --e)
			}, g)
		};
		return function() {
			a(this.dom.wrap[0], 40, 600);
			return this
		}
	}();
	var o = function() {
		var b = this,
			a = function(a) {
				var c = b[a];
				b[a] = function() {
					return c.apply(b, arguments)
				}
			};
		a("start");
		a("over");
		a("end")
	};
	o.prototype = {
		start: function(b) {
			c(document).bind("mousemove", this.over).bind("mouseup", this.end);
			this._sClientX = b.clientX;
			this._sClientY = b.clientY;
			this.onstart(b.clientX, b.clientY);
			return !1
		},
		over: function(b) {
			this._mClientX = b.clientX;
			this._mClientY = b.clientY;
			this.onover(b.clientX - this._sClientX, b.clientY - this._sClientY);
			return !1
		},
		end: function(b) {
			c(document).unbind("mousemove", this.over).unbind("mouseup", this.end);
			this.onend(b.clientX, b.clientY);
			return !1
		}
	};
	var j = c(window),
		k = c(document),
		i = document.documentElement,
		p = !! ("minWidth" in i.style) && "onlosecapture" in i,
		q = "setCapture" in i,
		r = function() {
			return !1
		}, n = function(b) {
			var a = new o,
				c = artDialog.focus,
				d = c.dom,
				g = d.wrap,
				h = d.title,
				e = g[0],
				f = h[0],
				i = d.main[0],
				l = e.style,
				s = i.style,
				t = b.target === d.se[0] ? !0 : !1,
				u = (d = "fixed" === e.style.position) ? 0 : k.scrollLeft(),
				v = d ? 0 : k.scrollTop(),
				n = j.width() - e.offsetWidth + u,
				A = j.height() - e.offsetHeight + v,
				w, x, y, z;
			a.onstart = function() {
				t ? (w = i.offsetWidth, x = i.offsetHeight) : (y = e.offsetLeft, z = e.offsetTop);
				k.bind("dblclick", a.end).bind("dragstart", r);
				p ? h.bind("losecapture", a.end) : j.bind("blur", a.end);
				q && f.setCapture();
				g.addClass("d-state-drag");
				c.focus()
			};
			a.onover = function(a, b) {
				if (t) {
					var c = a + w,
						d = b + x;
					l.width = "auto";
					s.width = Math.max(0, c) + "px";
					l.width = e.offsetWidth + "px";
					s.height = Math.max(0, d) + "px"
				} else c = Math.max(u, Math.min(n, a + y)), d = Math.max(v, Math.min(A, b + z)), l.left = c + "px", l.top = d + "px"
			};
			a.onend = function() {
				k.unbind("dblclick", a.end).unbind("dragstart", r);
				p ? h.unbind("losecapture", a.end) : j.unbind("blur", a.end);
				q && f.releaseCapture();
				g.removeClass("d-state-drag")
			};
			a.start(b)
		};
	c(document).bind("mousedown", function(b) {
		var a = artDialog.focus;
		if (a) {
			var c = b.target,
				d = a.config,
				a = a.dom;
			if (!1 !== d.drag && c === a.title[0] || !1 !== d.resize && c === a.se[0]) return n(b), !1
		}
	})
})(this.art || this.jQuery);

(function (d) {
d['okValue'] = '确定';
d['cancelValue'] = '取消';
d['title'] = '提示';
d['zIndex'] = '99999999';
// [more..]
})(art.dialog.defaults); 