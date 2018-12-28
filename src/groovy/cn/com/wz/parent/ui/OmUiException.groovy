package cn.com.wz.parent.ui

class OmUiException extends Exception{
	public OmUiException() {
		super()
	}

	public OmUiException(String message) {
		super(message)
	}

	public OmUiException(Throwable throwable) {
		super(throwable)
	}

	public OmUiException(String message, Throwable throwable) {
		super(message, throwable)
	}
}
