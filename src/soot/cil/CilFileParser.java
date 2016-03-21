package soot.cil;

import heros.solver.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soot.cil.ast.CilClass;
import soot.cil.ast.CilGenericDeclarationList;

/**
 * Parser for the global structures in a CIL disassembly file
 * 
 * @author Steven Arzt
 *
 */
public class CilFileParser {
	
	private final File file;
	private Map<String, CilClass> classes = new HashMap<String, CilClass>();
		
	public CilFileParser(File file) throws IOException {
		this.file = file;
		parse();
	}
	
	/**
	 * Parses the current CIL disassembly file and creates a list of classes
	 * @throws IOException Thrown if the file could not be read
	 */
	private void parse() throws IOException {
		BufferedReader rdr = null;
		try {
			int lineNum = 0;
			rdr = new BufferedReader(new FileReader(file));
			String line;
			List<Pair<Integer, CilClass>> classStack = new ArrayList<Pair<Integer, CilClass>>();
			int levelCounter = 0;
			while ((line = rdr.readLine()) != null) {
				try {
					line = line.trim();
					
					// Remove comments
					{
						int cmtIdx = line.indexOf("//");
						if (cmtIdx >= 0)
							line = line.substring(0, cmtIdx);
						if (line.isEmpty())
							continue;
					}
					
					// Scan for a class definition
					if (line.startsWith(".class")) {
						// Is this a generic class definition?
						CilGenericDeclarationList generics = Cil_Utils.parseGenericDeclaration(line);
						
						// Parse the parameters
						List<String> tokens = Cil_Utils.split(line, ' ');
						boolean isInterface = false;
						for (String token : tokens) {
							if (token.equals("interface"))
								isInterface = true;
							else if (!isReservedModifier(token)) {
								// This is a class name. Whatever follows afterwards
								// no longer belongs to the class name
								String className = token;
								if (!classStack.isEmpty() && classStack.get(0).getO1() == levelCounter - 1)
									className = classStack.get(0).getO2().getClassName() + "$" + className;
								
								CilClass clazz = new CilClass(className, lineNum, generics, isInterface);
								Cil_Utils.addClassToAssemblyMap(clazz.getUniqueClassName(), file.getAbsolutePath());
								classStack.add(0, new Pair<Integer, CilClass>(levelCounter, clazz));
								break;
							}
						}
					}
					
					// Keep the scan stack for nesting
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == '{') {
							levelCounter++;
						}
						else if (line.charAt(i) == '}') {
							if (levelCounter == 0)
								throw new RuntimeException("Stack underrun on line " + lineNum);
							levelCounter--;
							if (!classStack.isEmpty() && classStack.get(0).getO1() == levelCounter) {
								CilClass theClass = classStack.remove(0).getO2();
								theClass.setEndLine(lineNum + 1);
								classes.put(theClass.getUniqueClassName(), theClass);
							}
						}
					}
				}
				finally {
					lineNum++;
				}
			}
			
			if (levelCounter != 0)
				throw new RuntimeException("CIL file seems to be truncated");
		}
		finally {
			if (rdr != null)
				rdr.close();
		}
	}
	
	private boolean isReservedModifier(String token) {
		return token.equals("private")
				|| token.equals("public")
				|| token.equals("sealed")
				|| token.equals("nested")
				|| token.equals("ansi")
				|| token.equals("sequential")
				|| token.equals("auto")
				|| token.equals("beforefieldinit")
				|| token.equals("abstract")
				|| token.equals("extends")
				|| token.equals("interface")
				|| token.equals(".class");
	}
	
	/**
	 * Gets the classes declared in this CIL disassembly file
	 * @return The list of classes in the current CIL disassemblx file
	 */
	public Collection<CilClass> getClasses() {
		return this.classes.values();
	}
	
	/**
	 * Gets the parsed class with the given name
	 * @param className The name of the class to get
	 * @return The parsed data structure for the class with the given name if it
	 * exists, otherwise null
	 */
	public CilClass findClass(String className) {
		return this.classes.get(className);
	}
	
}
