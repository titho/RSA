public class Logger {
	private static boolean isQuietMode = false;
	
	public static void setQuietMode(boolean isQuietMode) {
		Logger.isQuietMode = isQuietMode;
	}

	public static void log(String message, boolean forcePrint) {
		if (isQuietMode && !forcePrint) {
			return;
		}
		System.out.println(message);
	}
	
	public static void log(String message) {
		log(message, false);
	}
}
