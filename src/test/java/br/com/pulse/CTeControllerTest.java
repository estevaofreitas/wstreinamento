package br.com.ithappens.contabil.rest.controller;

import br.com.ithappens.contabil.rest.model.dto.CTeDTO;
import br.com.ithappens.contabil.rest.service.CTeService;
import br.com.ithappens.contabil.rest.service.ICTeService;
import br.com.ithappens.contabil.rest.utils.exceptions.FalhaConversaoCTeException;
import br.com.ithappens.contabil.rest.utils.exceptions.ValidacaoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CTeControllerTest {

    private CTeController controller;
    private ICTeService service;

    @Test
    public void deveInvocarMetodoServiceParaProcessamentoViaDTO() throws ValidacaoException, FalhaConversaoCTeException, JsonProcessingException {
        CTeDTO dto = mock(CTeDTO.class);
        ArgumentCaptor<CTeDTO> captor = ArgumentCaptor.forClass(CTeDTO.class);
        controller.importarViaDadosAvulsos(dto);
        verify(service).processarViaDadosAvulsos(captor.capture());
        Assert.assertSame(dto, captor.getValue());
    }

    @Test
    public void deveInvocarMetodoServiceParaProcessamentoViaXML() throws ValidacaoException, FalhaConversaoCTeException, JsonProcessingException {
        String xml = "conteudo xml";
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        controller.importarViaXML(xml);
        verify(service).processarViaXML(captor.capture());
        Assert.assertEquals(xml, captor.getValue());
    }

    @Test
    public void deveInvocarMetodoServiceParaCancelamentoViaDTO() throws ValidacaoException, FalhaConversaoCTeException, JsonProcessingException {
        CTeDTO dto = mock(CTeDTO.class);
        ArgumentCaptor<CTeDTO> captor = ArgumentCaptor.forClass(CTeDTO.class);
        controller.cancelarViaDadosAvulsos(dto);
        verify(service).processarCancelamento(captor.capture());
        Assert.assertSame(dto, captor.getValue());
    }

    @Test
    public void deveInvocarMetodoServiceParaCancelamentoViaChaveAcesso() throws ValidacaoException, FalhaConversaoCTeException, JsonProcessingException {
        String chaveAcesso = "23202004230300394300940000123908843900323490";
        Long idUsuario = 260L;
        ArgumentCaptor<String> captorString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);
        controller.cancelarViaChaveAcesso(chaveAcesso, idUsuario);
        verify(service).processarCancelamento(captorString.capture(), captorLong.capture());
        Assert.assertEquals(chaveAcesso, captorString.getValue());
        Assert.assertEquals(idUsuario, captorLong.getValue());
    }

    @Before
    public void setUp() {
        service = mock(CTeService.class);
        controller = new CTeController(service);
    }
}