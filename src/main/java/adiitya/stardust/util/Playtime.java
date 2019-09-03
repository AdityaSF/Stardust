package adiitya.stardust.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Playtime {

	private final int days;
	private final int hours;
	private final int minutes;
	private final int seconds;

	private Playtime(int days, int hours, int minutes, int seconds) {

		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return IntStream.of(days, hours, minutes, seconds)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining(":"))
				.replaceAll("^(0:)+", "");
	}

	public static Playtime fromTerrariaFile(TerrariaFile file) {

		AtomicLong ms = new AtomicLong(file.readLong() / 10000L);
		int days = removeTime(ms, TimeUnit.DAYS);
		int hours = removeTime(ms, TimeUnit.HOURS);
		int minutes = removeTime(ms, TimeUnit.MINUTES);
		int seconds = removeTime(ms, TimeUnit.SECONDS);

		return new Playtime(days, hours, minutes, seconds);
	}

	private static int removeTime(AtomicLong base, TimeUnit unit) {

		long removed = unit.convert(base.get(), TimeUnit.MILLISECONDS);
		base.set(base.get() - unit.toMillis(removed));

		return (int) removed;
	}
}
