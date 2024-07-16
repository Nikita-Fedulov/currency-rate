package ru.mycompany.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.mycompany.DTO.CurrencyRateDTO;
import ru.mycompany.exception.CurrencyRateParsingException;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class CurrencyRateParserXml implements CurrencyRateParser {
    @Override
    public List<CurrencyRateDTO> parse(String ratesAsString) {
        var rates = new ArrayList<CurrencyRateDTO>();
        var dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            var db = dbf.newDocumentBuilder();

            try (var reader = new StringReader(ratesAsString)) {
                Document doc = db.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("Valute");

                for (var valuteIdx = 0; valuteIdx < list.getLength(); valuteIdx++) {
                    var node = list.item(valuteIdx);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        var element = (Element) node;

                        var rate = CurrencyRateDTO.builder()
                                .numCode(element.getElementsByTagName("NumCode").item(0).getTextContent())
                                .charCode(element.getElementsByTagName("CharCode").item(0).getTextContent())
                                .nominal(Integer.parseInt(element.getElementsByTagName("Nominal").item(0).getTextContent()))
                                .name(element.getElementsByTagName("Name").item(0).getTextContent())
                                .value(Double.parseDouble(element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".")))
                                .vunitRate(Double.parseDouble(element.getElementsByTagName("VunitRate").item(0).getTextContent().replace(",", ".")))
                                .build();
                        rates.add(rate);

                    }
                }
            }
        } catch (Exception ex) {
            log.error("xml parsing error, xml:{}", ratesAsString, ex);
            throw new CurrencyRateParsingException(ex);
        }
        return rates;
    }
}