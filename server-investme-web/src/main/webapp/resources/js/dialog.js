// MonkeyPatch provided to don't scroll the page
if (PrimeFaces.widget.Dialog) {
    PrimeFaces.widget.Dialog.prototype.returnFocus = function() {
        var el = this.focusedElementBeforeDialogOpened;
        if (el) {
            window.setTimeout(function() {
                el.focus({
                    preventScroll: true
                });
            }, 1000);
        }
    }
};