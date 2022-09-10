package algorithm.sort.demo;

import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lys on 2022/9/10.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 * 垃圾代码,不知道怎么优化
 */
public class SortLog {

	static BufferedReader bufferedReader;
	static BufferedReader bufferedReader2;
	static Map<Integer, BufferedReader> bufferedReaderMap = new HashMap<>();
	static BufferedWriter bufferedWriter;

	public static void main(String[] args) {
		try {
			final File file = new File("C:\\Users\\lyszh\\Desktop\\log1.txt");
			final FileInputStream fileInputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			bufferedReaderMap.put(0, bufferedReader);
			final File file2 = new File("C:\\Users\\lyszh\\Desktop\\log2.txt");
			final FileInputStream fileInputStream2 = new FileInputStream(file2);

			bufferedReader2 = new BufferedReader(new InputStreamReader(fileInputStream2));

			bufferedReaderMap.put(1, bufferedReader2);

			//输出
			final File filef = new File("C:\\\\Users\\\\lyszh\\\\Desktop\\\\logf.txt");
			final FileOutputStream fileOutputStream = new FileOutputStream(filef);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

			List<String[]> list = new ArrayList<>();
			final String[] part1 = readPart(0, 3);
			list.add(part1);
			final String[] part2 = readPart(1, 3);
			list.add(part2);
			//归并排序
			String[] mergePart = mergeSort(list, new HashMap<>());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	private static String[] mergeSort(List<String[]> parts, Map<Integer, Integer> map) throws IOException {

		int totalLength = 0;
		//记录每个数组的指针
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i) == null) {
				continue;
			}
			totalLength += parts.get(i).length;
			//指针初始化
			map.putIfAbsent(i, 0);
		}
		String[] merge = new String[totalLength];

		for (int i = 0; i < totalLength; i++) {
			Pair<Integer, String>[] lines = new Pair[parts.size()];
			for (int j = 0; j < parts.size(); j++) {
				//
				String[] part = parts.get(j);
				if (part == null) {
					//这个数组读完了..
					continue;
				}
				if (map.get(j) <= part.length - 1) {
					lines[j] = Pair.of(j, parts.get(j)[map.get(j)]);
				} else {
					part = readPart(j, 3);
					if (part == null) {
						//那么这个文件读完了..
					}
					parts.remove(j);
					parts.add(j, part);

					//把指针重新归0;
					map.put(j, 0);
				}
			}
			//获取lines的最小值
			Arrays.sort(lines, (a1, a2) -> {
				if (a1 == null) {
					return 1;
				}
				if (a2 == null) {
					return -1;
				}
				final long l = fetchTimestamp(a1.getRight());
				final long l1 = fetchTimestamp(a2.getRight());
				if (l > l1) {
					return 1;
				} else if (l == l1) {
					return 0;
				} else {
					return -1;
				}
			});

			if (lines[0] == null) {
				merge[i] = null;
			} else {
				merge[i] = lines[0].getRight();
				//对应的数组坐标+1
				map.put(lines[0].getLeft(), map.get(lines[0].getLeft()) + 1);
			}
		}
		for (int i = 0; i < merge.length; i++) {
			if (merge[0] == null) {
				//没有数据了
				bufferedWriter.close();
				return null;
			}
			if (merge[i] != null) {
				bufferedWriter.write(merge[i]);
				//换行
				bufferedWriter.write(0XA);
			}
		}

		mergeSort(parts, map);

		return merge;
	}

	public static String[] readPart(Integer index, int length) throws IOException {
		final BufferedReader bufferedReader = bufferedReaderMap.get(index);
		if (bufferedReader == null) {
			return null;
		}
		final String[] strings = new String[length];
		final int max = strings.length;
		String line = null;
		int i = 1;
		while (i <= max && (line = bufferedReader.readLine()) != null) {

			strings[i - 1] = line;
			i++;
		}
		if (i == 1) {
			bufferedReader.close();
			bufferedReaderMap.put(index, null);
			return null;
		}
		return strings;
	}

	public static long fetchTimestamp(String logLine) {
		if (logLine == null) {
			return 0L;
		}
		final String[] split = logLine.split("\\|");
		return parseTimestamp(split[0]);
	}

	public static long parseTimestamp(String time) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			final Date parse = simpleDateFormat.parse(time);
			return parse.getTime();
		} catch (ParseException e) {
			return 0L;
		}
	}

	public static String readFirstLine(BufferedReader bufferedReader) throws IOException {
		String startline = null;
		if ((startline = bufferedReader.readLine()) != null) {
			return startline;
		}
		bufferedReader.close();
		return null;
	}

	public String readLastLine(BufferedReader bufferedReader) {
		return null;
	}

	public static String readLastLineV1(File file) {
		// 存储结果
		StringBuilder builder = new StringBuilder();
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
			// 指针位置开始为0，所以最大长度为 length-1
			long fileLastPointer = randomAccessFile.length() - 1;
			// 从后向前读取文件
			for (long filePointer = fileLastPointer; filePointer != -1; filePointer--) {
				// 移动指针指向
				randomAccessFile.seek(filePointer);
				int readByte = randomAccessFile.readByte();
				if (0xA == readByte) {
					//  LF='\n'=0x0A 换行
					if (filePointer == fileLastPointer) {
						// 如果是最后的换行，过滤掉
						continue;
					}
					break;
				}
				if (0xD == readByte) {
					//  CR ='\r'=0x0D 回车
					if (filePointer == fileLastPointer - 1) {
						// 如果是倒数的回车也过滤掉
						continue;
					}
					break;
				}
				builder.append((char) readByte);
			}
		} catch (Exception e) {
//			log.error("file read error, msg:{}", e.getMessage(), e);
		}
		return builder.reverse().toString();
	}
}
