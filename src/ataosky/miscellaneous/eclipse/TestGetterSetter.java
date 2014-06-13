package ataosky.miscellaneous.eclipse;

public class TestGetterSetter {
private int age;
private String name;
private Double salary;
public TestGetterSetter(String name, Double salary, int age) {
	super();
	this.name = name;
	this.salary = salary;
	this.age = age;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	TestGetterSetter other = (TestGetterSetter) obj;
	if (age != other.age)
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (salary == null) {
		if (other.salary != null)
			return false;
	} else if (!salary.equals(other.salary))
		return false;
	return true;
}
public int getAge() {
	return age;
}
public String getName() {
	return name;
}
public Double getSalary() {
	return salary;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + age;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((salary == null) ? 0 : salary.hashCode());
	return result;
}
public void setAge(int age) {
	this.age = age;
}
public void setName(String name) {
	this.name = name;
}
public void setSalary(Double salary) {
	this.salary = salary;
}
@Override
public String toString() {
	return "TestGetterSetter [name=" + name + ", salary=" + salary + ", age="
			+ age + "]";
}
}
