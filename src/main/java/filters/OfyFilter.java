package filters;

import com.googlecode.objectify.ObjectifyFilter;

import javax.servlet.annotation.WebFilter;

/**
 * @author Giorgia Nadizar
 */
@WebFilter(asyncSupported = true, urlPatterns = {"/*"})
public class OfyFilter extends ObjectifyFilter {

}
