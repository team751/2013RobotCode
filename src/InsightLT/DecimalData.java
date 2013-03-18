/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InsightLT;

import com.sun.squawk.util.Arrays;

/**
 *
 * @author Wave
 */
public class DecimalData implements DisplayData{
     public DecimalData()
    {
        m_floatData = 0;
        m_precision = 2;
        m_floatHeader = "";
    };
    
    public DecimalData(String header)
    {
        m_updateString = true;
        m_floatHeader = header;
        m_precision = 2;
        m_floatData = 0;
    }
    
    public void setData(double data)
    {
        m_updateString = true;
        m_floatData = data;
    }
    
    public void setPrecision(int precision)
    {
        m_precision = precision;
    }
    
    public void setHeader(String header)
    {
        m_updateString = true;
        m_floatHeader = header;
    }
    
    public String getFormattedString(int zoneLength)
    {
        if(m_updateString)
	{
            m_updateString = false;
            String tmpString = formatDecimal(m_floatData);            
            
            if(m_floatHeader.length() + tmpString.length() > zoneLength)
            {                
                if(tmpString.length() > zoneLength)
                {
                        tmpString = tmpString.substring( 0, (tmpString.length() - (tmpString.length() - zoneLength) - 1));
                }
                m_formattedString = m_floatHeader.substring(0, zoneLength - tmpString.length()) + tmpString;
            }
            else
            {
                byte[] tmp = new byte[zoneLength - (m_floatHeader.length() + tmpString.length())];
                Arrays.fill(tmp, (byte)' ');
                m_formattedString = m_floatHeader + new String(tmp).concat(tmpString);
            }	
	}		
	return m_formattedString;
    }
    
    private String formatDecimal(double convert)
    {
        int exp = 10;
        for(int i = 1; i < m_precision; i++)
        {
            exp *= 10;
        }
        int wholePart = (int)convert;
        double decimalPart = convert - wholePart;
        int convertedDecimal = (int)((decimalPart * exp) + .5);
        String tmpDecimal = Integer.toString(convertedDecimal);
        if(tmpDecimal.length() < m_precision)
        {
            byte[] extraZeros = new byte[m_precision - tmpDecimal.length()];
            Arrays.fill(extraZeros, (byte)'0');
            tmpDecimal = new String(extraZeros).concat(tmpDecimal);
        }        
        return new String(Integer.toString(wholePart) + '.' + tmpDecimal);
    }
    
    private String m_formattedString;
    private String m_floatHeader;
    private double m_floatData;   
    private int m_precision;
    private boolean m_updateString;    
}
