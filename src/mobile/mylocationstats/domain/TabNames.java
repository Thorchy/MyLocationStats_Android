package mobile.mylocationstats.domain;

public enum TabNames {
	
	HOME ("HOME"),
	MAP ("MAP"),
	STATS ("STATS"),
	TARGETS ("TARGETS");
	
	private final String tab;
	
	private TabNames(String s) {
		tab = s;
	}
	
	public boolean equalsName(String otherName){
        return (otherName == null)? false:tab.equals(otherName);
    }

    public String toString(){
       return tab;
    }

}
