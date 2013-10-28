package datastr;

import java.util.ArrayList;
import java.util.TreeSet;

@SuppressWarnings("rawtypes")
public class CString implements Comparable {

        private int _hashCode;
        public String _string;
        
        public CString(String s) {
                _string = s.intern();
                _hashCode = _string.hashCode();
        }
        
        public int hashCode() {
                return _hashCode;
        }
        
        public boolean equals(Object o) {
                if (o instanceof CString)
                        return this._string == ((CString)o)._string;
                else
                        return false;
        }

        public int compareTo(Object o) {
                // TODO Auto-generated method stub
                if (o instanceof CString)
                        return this._string.compareTo(((CString)o)._string);
                else
                        return 0;
        }
        
        public String toString() {
                return _string;
        }
        
        public static TreeSet<CString> Convert2CString(TreeSet<String> a) {
                TreeSet<CString> ret = new TreeSet<CString>();
                for (String s : a)
                        ret.add(new CString(s));
                return ret;
        }
        
        public static ArrayList<CString> Convert2CString(ArrayList<String> a) {
                ArrayList<CString> ret = new ArrayList<CString>();
                for (String s : a)
                        ret.add(new CString(s));
                return ret;
        }

}