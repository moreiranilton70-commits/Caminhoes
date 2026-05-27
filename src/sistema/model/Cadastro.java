package sistema.model;

public class Cadastro {

    private int id;
    private String data;
    private String placa;
    private String numeroOF;
    private String horaCadastro;
    private String numeroPager;
    private String ofTroca;
    private String status;
    private String autorizacao;
    private String horaAutorizacao;
    private String observacao;
    private String usuario;

    private String usuarioAlteracao;
    private String horaAlteracao;

    public Cadastro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public String getNumeroOF() {
        return numeroOF;
    }

    public void setNumeroOF(String numeroOF) {
        this.numeroOF = numeroOF;
    }


    public String getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(String horaCadastro) {
        this.horaCadastro = horaCadastro;
    }


    public String getNumeroPager() {
        return numeroPager;
    }

    public void setNumeroPager(String numeroPager) {
        this.numeroPager = numeroPager;
    }


    public String getOfTroca() {
        return ofTroca;
    }

    public void setOfTroca(String ofTroca) {
        this.ofTroca = ofTroca;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }


    public String getHoraAutorizacao() {
        return horaAutorizacao;
    }

    public void setHoraAutorizacao(String horaAutorizacao) {
        this.horaAutorizacao = horaAutorizacao;
    }


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(String usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }


    public String getHoraAlteracao() {
        return horaAlteracao;
    }

    public void setHoraAlteracao(String horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }
}