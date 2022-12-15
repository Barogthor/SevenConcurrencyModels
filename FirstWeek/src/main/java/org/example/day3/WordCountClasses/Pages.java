/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/
package org.example.day3.WordCountClasses;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

public class Pages implements Iterable<Page> {
  private final int maxPages;
  private final String fileName;

  public Pages(int maxPages, String fileName) {
    this.maxPages = maxPages;
    this.fileName = fileName;
  }

  private class PageIterator implements Iterator<Page> {

    private XMLEventReader reader;
    private int remainingPages;
    private boolean endDocumentReached = false;

    public PageIterator() throws Exception {
      remainingPages = maxPages;
      reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(fileName));
    }

    public boolean hasNext() { return remainingPages > 0 && !this.endDocumentReached; }

    public Page next() {
      try {
        XMLEvent event;
        while (!this.endDocumentReached) {
          event = reader.nextEvent();
          if (event.isStartElement() && isPage(event))
            return readPage();
          else if( event.isEndElement() && event.asEndElement().isEndDocument())
            throw new NoSuchElementException();
        }
      }
      catch(NoSuchElementException ignored) {
        this.endDocumentReached = true;
        return new Page("","");
      }
      catch (XMLStreamException e) {
        throw new RuntimeException(e);
      }
      return new Page("","");
    }
    private static boolean isPage(XMLEvent event) {
      return event.asStartElement().getName().getLocalPart().equals("page");
    }

    private Page readPage() throws XMLStreamException {
      String title = "";
      String text = "";
      while (!this.endDocumentReached) {
        XMLEvent event = reader.nextEvent();
        if (event.isStartElement()) {
          if (isTitle(event))
            title = this.readTitle();
          else if (isRevision(event))
            text = extractTextFromRevision();
        } else if (event.isEndElement()) {
          if (event.asEndElement().getName().getLocalPart().equals("page")) {
            --remainingPages;
//            System.out.println(this.remainingPages + " pages max remaining.");
            return new Page(title, text);
          }
        }
      }
      return new Page("","");
    }

    private static boolean isTitle(XMLEvent event) {
      return event.asStartElement().getName().getLocalPart().equals("title");
    }

    private String readTitle() throws XMLStreamException {
      return reader.getElementText();
    }

    private static boolean isRevision(XMLEvent event) {
      return event.asStartElement().getName().getLocalPart().equals("revision");
    }

    private String extractTextFromRevision() throws XMLStreamException {
      while(!this.endDocumentReached) {
        XMLEvent event = reader.nextEvent();
        if(event.isStartElement()) {
          if(isText(event))
            return reader.getElementText();
        }
        else if (event.isEndElement()) {
          String name = event.asEndElement().getName().getLocalPart();
          if(name.equals("revision"))
            return "";
        }
      }
      return "";
    }

    private static boolean isText(XMLEvent event) {
      return event.asStartElement().getName().getLocalPart().equals("text");
    }


    public void remove() { throw new UnsupportedOperationException(); }
  }

  public Iterator<Page> iterator() {
    try {
      return new PageIterator();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}