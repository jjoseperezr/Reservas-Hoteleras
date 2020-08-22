
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
//import jdk.nashorn.tools.ShellFunctions.*;
//import static org.apache.jasper.tagplugins.jstl.core.Out.output;

@ManagedBean
@RequestScoped
public class User {

    int id;
    int cc;
    int cod;
    int cant;
    int precio;
    String name;
    String estado;
    String dates;
    String origentramite;
    String email;
    String password;
    String gender;
    String address;
    ArrayList usersList;
    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;

    public String getorigentramite() {
        return origentramite;
    }

    public void setorigentramite(String origentramite) {
        this.origentramite = origentramite;
    }

    public String getestado() {
        return estado;
    }

    public void setestado(String estado) {
        this.estado = estado;
    }

    public String getdates() {
        return dates;
    }

    public void setdates(String dates) {
        this.dates = dates;
    }

    public int getcc() {
        return cc;
    }

    public void setcc(int cc) {
        this.cc = cc;
    }

    public int getprecio() {
        return precio;
    }

    public void setprecio(int precio) {
        this.precio = precio;
    }

    public int getcod() {
        return cod;
    }

    public void setcod(int cod) {
        this.cod = cod;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://ec2-184-72-146-193.compute-1.amazonaws.com:3306/hotel", "root", "UniRemington2020");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public ArrayList ListadoClientes() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from clientes");
            while (rs.next()) {
                User user = new User();
                user.setcc(rs.getInt("cedula"));
                user.setName(rs.getString("nombre"));
                user.setEmail(rs.getString("correo"));
                user.setPassword(rs.getString("contrasena"));
                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }

    public ArrayList ListadoHabitaciones() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from habitaciones");
            while (rs.next()) {
                User user = new User();
                user.setcod(rs.getInt("codigo"));
                user.setName(rs.getString("nombre"));
                user.setAddress(rs.getString("descripcion"));
                user.setestado(rs.getString("Estado"));
                user.setPassword(rs.getString("personasxhabitacion"));
                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }

    public ArrayList ListadoHabitacionesDisponibles() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from habitaciones where estado='Disponible'");
            while (rs.next()) {
                User user = new User();
                user.setcod(rs.getInt("codigo"));
                user.setName(rs.getString("nombre"));
                user.setAddress(rs.getString("descripcion"));
                user.setestado(rs.getString("Estado"));
                user.setCant(rs.getInt("personasxhabitacion"));
                user.setprecio(rs.getInt("precio"));
                usersList.add(user);
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }

    public ArrayList ListadoHabitacionesReservadas() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from habitaciones where estado='Reservada'");
            while (rs.next()) {
                User user = new User();
                user.setcod(rs.getInt("codigo"));
                user.setName(rs.getString("nombre"));
                user.setAddress(rs.getString("descripcion"));
                user.setestado(rs.getString("Estado"));
                user.setPassword(rs.getString("personasxhabitacion"));
                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }

    public ArrayList ListadoHabitacionesReservadasCliente(int cedula) {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM habitaciones h, reserva r WHERE h.estado = 'Reservada' AND r.cedulacliente = '" + cedula + "'");
            while (rs.next()) {
                User user = new User();
                user.setcod(rs.getInt("codigo"));
                user.setName(rs.getString("nombre"));
                user.setAddress(rs.getString("descripcion"));
                user.setestado(rs.getString("estado"));
                user.setPassword(rs.getString("personasxhabitacion"));
                usersList.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return usersList;
    }

    public ArrayList listadosolicitudescliente(String correo) {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from solicitudes where cedula=" + correo);
            while (rs.next()) {
                User user = new User();
                user.setcc(rs.getInt("cedula"));
                user.setName(rs.getString("nombre"));
                user.setEmail(rs.getString("correo"));
                user.setdates(rs.getString("fechanacimiento"));
                user.setestado(rs.getString("estadosolicitud"));
                user.setorigentramite(rs.getString("origentramite"));

                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }

    private String output;

    public void contrasena() {
        output = "Error de inicio de sección, usuario sin registrar o contraseña incorrectos";
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    
    
    
    public String login(int cedula, String contrasena) {
        User user = null;
        if (cedula == (127001) && (contrasena.equals("admin"))) {
            return "/Admin.xhtml?faces-redirect=true";
        } else {
            try {
                usersList = new ArrayList();
                connection = getConnection();
                Statement stmt = getConnection().createStatement();
                ResultSet rs = stmt.executeQuery("Select cedula,nombre from clientes where cedula='" + cedula + "' and contrasena='" + contrasena + "'");
                while (rs.next()) {
                    int idcliente = -1;
                    idcliente = rs.getInt(1);
                    listadosolicitudes(idcliente);
                    user = new User();
                    user.setcc(rs.getInt("cedula"));
                    user.setName(rs.getString("nombre"));
                    sessionMap.put("editUser", user);
                    connection.close();

                    return "/HabitacionesDisponibles.xhtml?faces-redirect=true";//"/Solicitud_Tarjeta.xhtml?faces-redirect=true";  

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        contrasena();
        return null;

    }

    
    
    public String registrar() {
        int result = 0;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into clientes(cedula,nombre,correo,contrasena) values(?,?,?,?)");
            stmt.setInt(1, cc);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, password);
            result = stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "CrearCliente.xhtml?faces-redirect=true";
        }
    }

    
    
    public String crearhabitacion() {
        int result = 0;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into habitaciones (codigo,nombre,descripcion,personasxhabitacion,precio,estado) values(?,?,?,?,?,?)");
            stmt.setInt(1, cod);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setInt(4, cant);
            stmt.setInt(5, precio);
            stmt.setString(6, "Disponible");
            result = stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            return "Admin.xhtml?faces-redirect=true";
        } else {
            return "CrearCliente.xhtml?faces-redirect=true";
        }
    }

    
    
    public String Reservar(int cod, int cc, int precio) {
        int result = 0;
        try {
            connection = getConnection();

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO reserva (codigoreserva, cedulacliente, codigohabitacion, totalreserva) VALUES (0," + cc + "," + cod + "," + precio + ")");
            result = stmt.executeUpdate();
            connection.close();
            realizarReserva(cod);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            return "ReservadasCliente.xhtml?faces-redirect=true";
        } else {
            return "HabitacionesDisponibles.xhtml";
        }
    }

    
    
    
    public String edit(int id) {
        User user = null;
        System.out.println(id);
        try {
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from users where id = " + (id));
            rs.next();
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setGender(rs.getString("gender"));
            user.setAddress(rs.getString("address"));
            user.setPassword(rs.getString("password"));
            System.out.println(rs.getString("password"));
            sessionMap.put("editUser", user);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Editar_Cliente.xhtml?faces-redirect=true";
    }

    
    
    public String editatsolicitud(int id) {
        User user = null;
        System.out.println(id);
        try {

            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from solicitudes where cedula = " + (id));
            rs.next();
            user = new User();
            user.setcc(rs.getInt("cedula"));
            user.setName(rs.getString("nombre"));
            user.setEmail(rs.getString("correo"));
            user.setdates(rs.getString("fechanacimiento"));
            user.setorigentramite(rs.getString("origentramite"));
            user.setestado(rs.getString("estadosolicitud"));

            sessionMap.put("editUser", user);
            connection.close();
            return "/Estado_Solicitud.xhtml";
        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }

    
    
    public String listadosolicitudes(int id) {
        User user = null;
        System.out.println(id);
        try {
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select name,email from users where id = " + (id));
            rs.next();
            user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            sessionMap.put("user", user);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Listado_Solicitudes_Cliente.xhtml"; 
    }

    
    
    public String newcreditcard(String correocliente) {
        User user = null;
        System.out.println(correocliente);
        try {
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select name,email from users where correo = " + (correocliente));
            rs.next();
            user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            sessionMap.put("user", user);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Solicitud_Tarjeta.xhtml?faces-redirect=true"; 
    }

    
    
    
    public String update(User u) {
        
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("update users set name=?,email=?,password=?,gender=?,address=? where id=?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getGender());
            stmt.setString(5, u.getAddress());
            stmt.setInt(6, u.getId());
            stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";
    }

    
    
    public String actualizarsolicitud(User u) {
        
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("update solicitudes set estadosolicitud=? where cedula=?");
            stmt.setString(1, u.getestado());
            stmt.setInt(2, u.getcc());
            stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Listado_Solicitudes.xhtml?faces-redirect=true";
    }

    
    
    
    public void eliminarcliente(int cc) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from clientes where cedula = " + cc);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    
    public void eliminarhabitacion(int cod) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from habitaciones where codigo = " + cod);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    
    public void finalizarReserva(int cod) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE habitaciones SET estado='Disponible' WHERE codigo=" + cod);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    
    public void realizarReserva(int cod) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE habitaciones SET Estado='Reservada' WHERE codigo=" + cod);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    
    public void deletesolicitud(int cc) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from solicitudes where cedula = " + cc
                    + " and estadosolicitud= 'Rechazada'");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    
    
    public String getGenderName(char gender) {
        if (gender == 'M') {
            return "Masculino";
        } else {
            return "Femenino";
        }
    }

    
    
    public String getorigendeltramite(char origen) {
        if (origen == 'V') {
            return "Virtualmente";
        } else if (origen == 'T') {
            return "Telefonicamente";
        } else {
            return "En oficina";
        }
    }

}
