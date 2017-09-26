package com.gy.model;

/**
 * @author Chencongye
 * @version 0.0.1
 * @date 2017-9-20
 * @introduce 这是角色实体类
 */
public class Role {
	
	/**
	 * 创建角色主键生成策略 
	 */
	private int roleid;

	/**
	 * 创建角色名称
	 */
	private String rolename;
	
	/**
	 * 创建角色对应的权限
	 */
	private String permissions;
	
	/**
	 * 创建角色类型
	 */
	private String roletype;
	
	/**
	 * 创建无参构造函数
	 */
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 创建带有参数的构造函数
	 * @param roleid
	 * @param rolename
	 * @param permissions
	 * @param roletype
	 */
	public Role(int roleid, String rolename, String permissions, String roletype) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
		this.permissions = permissions;
		this.roletype = roletype;
	}

	/**
	 * 创建主键生成策略get方法
	 * @return
	 */
	public int getRoleid() {
		return roleid;
	}
	
	/**
	 * 创建主键生成策略set方法
	 * @return
	 */
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	/**
	 * 创建角色生成策略get方法
	 * @return
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * 创建角色生成策略set方法
	 * @return
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 创建角色权限生成策略get方法
	 * @return
	 */
	public String getPermissions() {
		return permissions;
	}
	
	/**
	 * 创建角色权限生成策略set方法
	 * @return
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	/**
	 * 创建角色类型生成策略get方法
	 * @return
	 */
	public String getRoletype() {
		return roletype;
	}

	/**
	 * 创建角色类型生成策略set方法
	 * @return
	 */
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	/**
	 * 创建角色的toString方法
	 * @return
	 */
	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", rolename=" + rolename
				+ ", permissions=" + permissions + ", roletype=" + roletype
				+ "]";
	}
}
