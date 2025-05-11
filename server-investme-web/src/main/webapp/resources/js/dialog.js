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

function hideBodyScroll() {
    document.body.style.overflow = 'hidden';
}

function showBodyScroll() {
    document.body.style.overflow = 'auto';
}

function recenterDialog(widget) {
	PF(widget).initPosition();
}