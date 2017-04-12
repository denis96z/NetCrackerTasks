package com.netcracker.edu.java.tasks;

import java.util.ArrayList;
import java.util.Iterator;

public class TreeNodeImpl implements TreeNode {
	private boolean expanded = false;
	
	private Object data = null;
	
	private TreeNode parent = null;
	private ArrayList<TreeNode> children = null;
	
	@Override
	public TreeNode getParent() {
		return parent;
	}
	
	@Override
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	@Override
	public TreeNode getRoot() {
		TreeNode root = null;
		
		if (parent != null) {
			root = parent.getRoot();
			if (root == null) {
				root = parent;
			}
		}
		
		return root;
	}
	
	@Override
	public boolean isLeaf() {
		return children == null ? true : children.size() == 0;
	}
	
	@Override
	public int getChildCount() {
		return children == null ? 0 : children.size();
	}
	
	@Override
	public Iterator<TreeNode> getChildrenIterator() {
		return children == null ? null : children.iterator();
	}
	
	@Override
	public void addChild(TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		child.setParent(this);
		children.add(child);
	}
	
	@Override
	public boolean removeChild(TreeNode child) {
		int index = children.indexOf(child);
		boolean result = index >= 0;
		
		if (result) {
			children.get(index).setParent(null);
			children.remove(index);
		}
		
		return result;
	}
	
	@Override
	public boolean isExpanded() {
		return expanded;
	}
	
	@Override
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
		for (TreeNode child : children) {
			child.setExpanded(expanded);
		}
	}
	
	@Override
	public Object getData() {
		return data;
	}
	
	@Override
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String getTreePath() {
		String path = null;
		
		String current = data == null ? "empty" : data.toString();		
		if (parent == null) {
			path = current;
		}
		else {
			path = parent.getTreePath() + "->" + current;
		}
		
		return path;
	}
	
	@Override
	public TreeNode findParent(Object data) {
		if (this.data.equals(data)) {
			return this;
		}
		else if (parent != null) {
			return parent.findParent(data);
		}
	
		return null;
	}
	
	@Override
	public TreeNode findChild(Object data) {
		for (TreeNode child : children) {
			if (child.getData() != null) {
				if (child.getData().equals(data)) {
					return child;
				}
			}
			else if (data == null) {
				return child;
			}
			else {
				TreeNode result = child.findChild(data);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		TreeNodeImpl node = new TreeNodeImpl();
		node.setParent(new TreeNodeImpl());
		System.out.println(node.getRoot());
	}
}
