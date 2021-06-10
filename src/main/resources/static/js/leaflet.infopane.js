L.Control.InfoPane = L.Control.extend({
  includes: L.Mixin.Events,
  options: {
    position: "topright"
  },
  initialize: function(b, a) {
    this._placeholder = b;
    L.setOptions(this, a);
    this._paneOnLeftSide = (this.options.position.indexOf("left") !== -1)
  },
  onAdd: function(b) {
    var a = this._createControl();
    this._createPane(this._placeholder, b);
    return a
  },
  onRemove: function(a) {
    this.hidePane(a);
    this._clearInfoPane(a);
    this._clearControl();
    return this
  },
  _createControl: function() {
    var d = "leaflet-info-control",
      a = L.DomUtil.create("div", d);
    var b = this._openButton = L.DomUtil.create("a", "button", a);
    b.href = "#";
    b.title = "Information";
    var c = L.DomEvent.stopPropagation;
    L.DomEvent.on(b, "click", c).on(b, "mousedown", c).on(b, "touchstart", c).on(b, "mousewheel", c).on(b, "MozMousePixelScroll", c);
    L.DomEvent.on(b, "click", L.DomEvent.preventDefault);
    L.DomEvent.on(b, "click", this.showPane, this);
    return a
  },
  _clearControl: function() {
    var a = this._openButton;
    var b = L.DomEvent.stopPropagation;
    L.DomEvent.off(a, "click", b).off(a, "mousedown", b).off(a, "touchstart", b).off(a, "mousewheel", b).off(a, "MozMousePixelScroll", b);
    L.DomEvent.off(a, "click", L.DomEvent.preventDefault);
    L.DomEvent.off(a, "click", this.showPane)
  },
  _createPane: function(h, a) {
    var d = this;
    var e = this._contentContainer = L.DomUtil.get(h);
    e.parentNode.removeChild(e);
    var c = a.getContainer();
    var f = "leaflet-info-pane",
      b = this._pane = L.DomUtil.create("div", f, c);
    if (this._paneOnLeftSide) {
      L.DomUtil.addClass(b, "left")
    } else {
      L.DomUtil.addClass(b, "right")
    }
    b.appendChild(e);
    L.DomUtil.addClass(e, "content");
    var g = L.DomEvent.stopPropagation;
    L.DomEvent.on(b, "click", g).on(b, "dblclick", g).on(b, "mousedown", g).on(b, "touchstart", g).on(b, "mousewheel", g).on(b, "MozMousePixelScroll", g);
    var i = this._closeButton = L.DomUtil.create("a", "close", b);
    i.innerHTML = "&times;";
    L.DomEvent.on(i, "click", this.hidePane, this);
    return b
  },
  _clearPane: function(c) {
    var a = L.DomEvent.stopPropagation;
    L.DomEvent.off(this._panel, "click", a).off(this._panel, "dblclick", a).off(this._panel, "mousedown", a).off(this._panel, "touchstart", a).off(this._panel, "mousewheel", a).off(this._panel, "MozMousePixelScroll", a);
    L.DomEvent.off(this._closeButton, "click", this.hidePanel);
    var b = c.getContainer();
    b.removeChild(this._pane);
    this._panel = null
  },
  isPaneVisible: function() {
    return L.DomUtil.hasClass(this._pane, "visible")
  },
  showPane: function() {
    if (!this.isPaneVisible()) {
      L.DomUtil.addClass(this._pane, "visible");
      this._map.panBy([this.getOffset() * 0.5, 0], {
        duration: 0.5
      });
      this.fire("show")
    }
  },
  hidePane: function(a) {
    if (this.isPaneVisible()) {
      L.DomUtil.removeClass(this._pane, "visible");
      if (null !== this._map) {
        this._map.panBy([this.getOffset() * -0.5, 0], {
          duration: 0.5
        })
      }
      this.fire("hide");
      if (a) {
        L.DomEvent.stopPropagation(a)
      }
    }
  },
  getOffset: function() {
    if (this._paneOnLeftSide) {
      return -this._pane.offsetWidth
    } else {
      return this._pane.offsetWidth
    }
  },
});
L.control.infoPane = function(b, a) {
  return new L.Control.InfoPane(b, a)
};
