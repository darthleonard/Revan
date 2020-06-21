//package dl.reflection;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//
//import dl.core.gui.screen.IScreenAdapter;
//
//public class DlReflection {
//	public static void Reflect() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		@SuppressWarnings("rawtypes")
//		var<Class> allClasses = new ArrayList<Class>();
//		var packages = Package.getPackages();
//		for (var pkg : packages) {
//			if(pkg.toString().contains("dl.")) {
//				// Recursively descend
//				for(var c : ReflectionTest.getClasses(pkg.getName())) {
//					allClasses.add(c);
//				}
//			}
//		}
//		for(var i : allClasses) {
//			for(var _interface : i.getInterfaces()) {
//				if(_interface == IScreenAdapter.class) {
//					@SuppressWarnings("unchecked")
//					var obj = (IScreenAdapter)i.getDeclaredConstructor().newInstance();
//					System.out.println(obj.getTitle());
//				}
//			}
//		}
//	}
//}
