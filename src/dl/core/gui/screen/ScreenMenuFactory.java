package dl.core.gui.screen;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dl.core.gui.menu.MenuNode;

public class ScreenMenuFactory {
	private String menuDefinitionPath;
	
	public ScreenMenuFactory(String menuDefinitionPath) {
		this.menuDefinitionPath = menuDefinitionPath;
	}
	
	public JTree Create() {
    	Node root = null;
        try {
          var factory = DocumentBuilderFactory.newInstance();
          var builder = factory.newDocumentBuilder();
          var doc = builder.parse(menuDefinitionPath);
          root = (Node) doc.getDocumentElement();
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null,
        		  ex.getMessage(),
        		  "Error",
              JOptionPane.ERROR_MESSAGE);
          return null;
        }
        if (root != null) {
        	var dtModel = new DefaultTreeModel(builtTreeNode(root));
            var tr = new JTree();
            tr.setRootVisible(false);
            tr.setModel(dtModel);
            return tr;
        }
        return null;
    }
    
    private DefaultMutableTreeNode builtTreeNode(Node root) {
        DefaultMutableTreeNode dmtNode;

        if(root.getAttributes() == null) {
        	dmtNode = new MenuNode(root.getNodeName());
        } else {
        	var aux = root.getAttributes();
            if(aux.getNamedItem("screen") != null) {
            	var screenName = aux.getNamedItem("screen").getTextContent();
          	  	try {
					dmtNode = new MenuNode(root.getTextContent(), Class.forName(screenName));
				} catch (ClassNotFoundException e) {
					dmtNode = new MenuNode(root.getNodeName());
					JOptionPane.showMessageDialog(null, e.getClass() + ":\n" + e.getMessage());
				}
            } else {
            	dmtNode = new MenuNode(root.getNodeName());
            }
        }
        
        NodeList nodeList = root.getChildNodes();
        for (int count = 0; count < nodeList.getLength(); count++) {
          Node tempNode = nodeList.item(count);
          if (tempNode.getNodeType() == Node.ELEMENT_NODE && tempNode.hasChildNodes()) {
              dmtNode.add(builtTreeNode(tempNode));
          }
        }
        
        return dmtNode;
      }
}
