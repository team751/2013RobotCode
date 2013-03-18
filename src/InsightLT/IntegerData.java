/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InsightLT;

import com.sun.squawk.util.Arrays;
/**
 *
 * @author Bit Built Tech
 */
public class IntegerData implements DisplayData {
    public IntegerData()
    {
        m_updateString = true;
        m_integerData = 0;
        m_integerHeader = "";
    };
    
    public IntegerData(String header)
    {
        m_updateString = true;
        m_integerHeader = header;
        m_integerData = 0;
    }
    
    public void setData(int data)
    {
        m_updateString = true;
        m_integerData = data;
    }
    
    public void setHeader(String header)
    {
        m_updateString = true;
        m_integerHeader = header;
    }
    
    public String getFormattedString(int zoneLength)
    {
        if(m_updateString)
	{
            m_updateString = false;
            String tmpString = Integer.toString(m_integerData);
            if(m_integerHeader.length() + tmpString.length() > zoneLength)
            {                
                if(tmpString.length() > zoneLength)
                {
                        tmpString = new String( tmpString.substring( 0, (tmpString.length() - (tmpString.length() - zoneLength) - 1)));
                }
                m_formattedString = m_integerHeader.substring(0, zoneLength - tmpString.length()) + tmpString;
            }
            else
            {
                byte[] tmp = new byte[zoneLength - (m_integerHeader.length() + tmpString.length())];
                Arrays.fill(tmp, (byte)' ');
                m_formattedString = m_integerHeader + new String(tmp).concat(tmpString);
            }	
	}		
	return m_formattedString;
    }
    
    private String m_formattedString;
    private String m_integerHeader;
    private int m_integerData;    
    private boolean m_updateString;    
}
