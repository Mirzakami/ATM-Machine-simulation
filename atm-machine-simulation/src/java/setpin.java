    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.sql.*;

/**
 *
 * @author SANTHOSH M
 */
@WebServlet("/setpinurl")
public class setpin extends HttpServlet {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/atmmachine", "root", "santhosh010624@BI");
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = res.getWriter()) {

            String action = req.getParameter("action");

            if (action == null) {
                res.sendRedirect("index.html");
                return;
            }
            try {
                switch (action) {
                    case "setpin":
                        setpin(req, res, out);
                        break;
                    case "atm":
                        atm(req, res, out);
                        break;
                    case "deposit":
                        deposit(req, res, out);
                        break;
                    case "withdraw":
                        withdraw(req, res, out);
                        break;
                    case "balance":
                        balance(req, res, out);
                        break;
                    case "changepin":
                        changepin(req, res, out);
                        break;
                    default:
                        res.sendRedirect("index.html");
                }
            } catch (Exception ex) {
                showerrorpopup(res, "Error:" + ex.getMessage(), "setpin.html");
            }
        }
    }

    private void setpin(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws Exception {
        String uname = req.getParameter("name");
        String password = req.getParameter("pwd");
        String cpassword = req.getParameter("pwd2");
        if (password.equals(cpassword)) {

            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO setpin(name,pin,cpin,balance) VALUES(?,?,?,0.00);");
                ps.setString(1, uname);
                ps.setString(2, password);
                ps.setString(3, cpassword);

                int rowInserted = ps.executeUpdate();

                con.close();
                ps.close();

                showsuccesspopup(res, "PIN added Successfully!", "index.html");

            } catch (SQLException ex) {
                showerrorpopup(res, "Error:" + ex.getMessage(), "index.html");
            }
        } else {
            showerrorpopup(res, "Password is doesn't match", "setpin.html");
        }
    }

    private void atm(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws IOException, Exception {

        String Nname = req.getParameter("Nname");
        String Npin = req.getParameter("Npin");

//        if (Nname.equals(uname) && Npin.equals(password)) {
//            showsuccesspopup(res, "Welcome " + Nname, "atmoperation.html");
//        } else {
//            showerrorpopup(res, "Name or PIN is Invalid ,Enter Valid Name or PIN", "atm.html");
//        }
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT*FROM setpin WHERE name=? AND pin=?;");
            ps.setString(1, Nname);
            ps.setString(2, Npin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("Nname", Nname);
                session.setAttribute("Npin", Npin);

                showsuccesspopup(res, "welcom " + Nname, "atmoperation.html");

//                HttpSession session = req.getSession();
//                session.setAttribute("Nname",Nname);
//                res.sendRedirect("atmprocessurl");
            } else {
                showerrorpopup(res, "Name or PIN is Invalid, Enter Valid Name or PIN", "atm.html");
            }

            con.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            showerrorpopup(res, "ERROR:" + ex.getMessage(), "atm.html");
        }
    }

    public void deposit(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws IOException, Exception {

        HttpSession session = req.getSession(false);

//        if (session == null || session.getAttribute("Nname") == null) {
//            showerrorpopup(res, "please login first!", "atm.html");
//            return;
//        }
        String username = (String) session.getAttribute("Nname");
        String userpin = (String) session.getAttribute("Npin");
        double amount = Double.parseDouble(req.getParameter("amount"));

        if (amount < 100) {
            showerrorpopup(res, "Amount must be greater than 100", "deposit.html");
            return;
        }
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE setpin SET balance=balance+? WHERE name=? AND pin=? ;");
            ps.setDouble(1, amount);
            ps.setString(2, username);
            ps.setString(3, userpin);
            double newamount = ps.executeUpdate();

            if (0 < newamount) {
                showsuccesspopup(res, "Amount Added Succussfully!! " + amount, "atmoperation.html");
            } else {
                showerrorpopup(res, "Somthing error , Enter again!", "deposit.html");
            }
            ps.close();
            con.close();
        } catch (SQLException ex) {
            showerrorpopup(res, "ERROR:" + ex.getMessage(), "deposit.html");
        }
    }

    public void withdraw(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws Exception {
        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("Nname");
        String userpin = (String) session.getAttribute("Npin");
        double amount = Double.parseDouble(req.getParameter("amount"));

        if (amount <= 100) {
            showerrorpopup(res, "Invaid Amount ,Enter greater than 100", "withdraw.html");
            return;
        }
        try {

            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM setpin WHERE name=? AND pin=? ; ");
            ps.setString(1, username);
            ps.setString(2, userpin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");

                if (currentBalance >= amount) {

                    PreparedStatement wps = con.prepareStatement("UPDATE setpin SET balance= balance - ? WHERE name=? AND pin=? ;");
                    wps.setDouble(1, amount);
                    wps.setString(2, username);
                    wps.setString(3, userpin);
                    int rows = wps.executeUpdate();

                    if (rows > 0) {
                        showsuccesspopup(res, "Take Your Cash " + amount, "atmoperation.html");
                    }
                    wps.close();
                } else {
                    showerrorpopup(res, "Insufficient Balance!", "deposit.html");
                }
                con.close();
                ps.close();
                rs.close();
            }

        } catch (Exception ex) {
            showerrorpopup(res, "ERROR:" + ex.getMessage(), "withdraw.html");
        }
    }

    public void balance(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws Exception {

        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("Nname");
        String userpin = (String) session.getAttribute("Npin");

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM setpin WHERE name=? AND pin=?;");
            ps.setString(1, username);
            ps.setString(2, userpin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                showsuccesspopup(res, "Your Balance is: " + currentBalance, "atmoperation.html");
            }
        } catch (Exception ex) {
            showerrorpopup(res, "ERROR: " + ex.getMessage(), "atmoperation.html");
        }
    }

    public void changepin(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws Exception {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("Nname");
        String newPin = req.getParameter("newPin");
        String newcPin = req.getParameter("newConfirmPin");
        
        if(!newPin.equals(newcPin)){
            showerrorpopup(res, "PIN Should be same.","changepin.html");
            return;
        }
        try{
          Connection con = getConnection();
          PreparedStatement ps = con.prepareStatement("UPDATE setpin SET pin=? ,cpin=? WHERE name=? ;");
          ps.setString(1, newPin);
          ps.setString(2, newcPin);
          ps.setString(3, username);
          
          int updaterow = ps.executeUpdate();
          
          if(updaterow>0){
            showsuccesspopup(res, "PIN Changed Successfully!!","atmoperation.html");
            
          }else{
             showerrorpopup(res, "you must Enter the PIN","changepin.html");
          }
          ps.close();
          con.close();
        }catch(Exception ex){showerrorpopup(res,"ERROR:"+ ex.getMessage(),"atmoperation.html");}
        
        
    }

    private void showsuccesspopup(HttpServletResponse res, String message, String redirectPage) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<script type='text/javascript'>");
        out.println("alert('" + message + "');");
        out.println("window.location.href='" + redirectPage + "';");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        out.println("</body>");
        out.println("</html>");
    }

    private void showerrorpopup(HttpServletResponse res, String message, String redirectpage) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head><title>Error</title></head>");
        out.println("<body>");
        out.println("<h2 style='color:red'>ERROR: " + message + "</h2>");
        out.println("<p><a href='" + redirectpage + "'>Click here to try again</a></p>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ATM set PIN servlet";
    }// </editor-fold>

}
