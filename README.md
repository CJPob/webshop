Use Intelij Ultimate
Download Tomcat 10.1.30
Use 21 oracle openjdk 21


#temporary comment, forgot to create a branch so summing up here:" 


**Major fixes and new features:**

**1. ITEM:**
- Fixed broken ItemServlet.
- Added new methods in Handler and DB.

**2. Fixed Admin menu and AdminServlet.**

**3. Fixed Warehouse (Admin):**
- Layout; 
- Can now see all items in the warehouse, including those with a balance of 0.
- Able to filter items by type 
- Able to change item quantity

**4.  Fixed "Set User Role" for Admin:**
- Layout; 
- Can now see all users and set new roles.

**5.  Fixed broken redirections in UserServlet.**

**6.  Fixed current (logged in) user details display and logout button:**
- LogoutServlet now cleans the session and redirects to the login menu.

**7. Fixed all broken HTML layouts and CSS style elements in all relevant JSP files.**

**8. Main page now displays ONLY ITEMS IN STOCK, not those with zero balance; also updated HTML and CSS for the main page.**  

**9. Added relevant methods in UserServlet to set roles.** 

**PS: Had no time to fix:**
- role-based access to features and menus; 
- the broken CartServlet and proper redirections; 
- keeping items in session untill user logs in, which had disappeared after several rebases; 
- broken cart-related redirections, will take care of it tomorrow. 😴😴😴
