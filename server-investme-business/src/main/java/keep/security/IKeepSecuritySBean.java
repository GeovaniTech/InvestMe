package keep.security;

import jakarta.ejb.Local;

@Local
public interface IKeepSecuritySBean {
	public boolean isClientBlocked(String email);
}
